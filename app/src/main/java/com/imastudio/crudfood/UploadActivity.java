package com.imastudio.crudfood;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.imastudio.crudfood.model.ResponseFood;
import com.imastudio.crudfood.network.ConfigRetrofit;

import java.io.File;
import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UploadActivity extends AppCompatActivity {

    @BindView(R.id.img_result)
    ImageView imgResult;
    @BindView(R.id.btn_camera)
    Button btnCamera;
    @BindView(R.id.btn_gallery)
    Button btnGallery;
    @BindView(R.id.btn_upload)
    Button btnUpload;

    String photoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_camera, R.id.btn_gallery, R.id.btn_upload})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_camera:
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentCamera, 1);
                break;
            case R.id.btn_gallery:
                Intent intentGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intentGallery, 2);
                break;
            case R.id.btn_upload:
                setUploadGambar();
                break;
        }
    }

    private void requestMediaFilePermission() {
        if (askForPermission(Manifest.permission.READ_EXTERNAL_STORAGE, 1)){
            Toast.makeText(this, "Allowed", Toast.LENGTH_SHORT).show();
        }else {
            displayPermission(Manifest.permission.READ_EXTERNAL_STORAGE,1);
        }
    }

    private void displayPermission(String permission, int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(UploadActivity.this, permission)){
            ActivityCompat.requestPermissions(UploadActivity.this, new String[]{permission}, requestCode);
        }else {
            ActivityCompat.requestPermissions(UploadActivity.this, new String[]{permission}, requestCode);
        }
    }

    private boolean askForPermission(String permission, int i) {
        if (ContextCompat.checkSelfPermission(UploadActivity.this, permission) != PackageManager.PERMISSION_GRANTED){
            return false;
        }else {
            return true;
        }
    }

    private void setUploadGambar() {
        MultipartBody.Part gambar = null;
        if (photoPath != null){
            File file = new File(photoPath);

            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            gambar = MultipartBody.Part.createFormData("gambar", file.getName(), requestFile);

            ConfigRetrofit configRetrofit = new ConfigRetrofit();
            configRetrofit.service.uploadGambar(gambar).enqueue(new Callback<ResponseFood>() {
                @Override
                public void onResponse(Call<ResponseFood> call, Response<ResponseFood> response) {

                    if (response.isSuccessful()){
                        Toast.makeText(UploadActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(UploadActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseFood> call, Throwable t) {

                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                imgResult.setImageBitmap(bitmap);
            }
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
        }else if (requestCode == 2){
            if (resultCode == RESULT_OK){
                Uri uri = data.getData();

                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(
                  uri,
                  filePathColumn,
                        null,
                        null,
                        null);

                assert  cursor != null;
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                photoPath = cursor.getString(columnIndex);
                Toast.makeText(this, photoPath, Toast.LENGTH_SHORT).show();
                imgResult.setImageURI(uri);
                cursor.close();

            }else if (resultCode == RESULT_CANCELED){
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {

            if (requestCode == 1) {
                requestMediaFilePermission();
            }

        }
        // this else statment will run wen user click on dont ask again and deined the permission and still click on allow button
        else {
            Toast.makeText(this, "Enable Message permission from Setting", Toast.LENGTH_SHORT).show();
        }
    }

}
