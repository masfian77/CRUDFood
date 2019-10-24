package com.imastudio.crudfood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.imastudio.crudfood.model.ResponseFood;
import com.imastudio.crudfood.network.ConfigRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateDeleteActivity extends AppCompatActivity {

    TextView tvId;
    EditText edtUpdateNama, edtUpdateHarga, edtUpdateUrl;
    Button btnUpdate, btnDelete;

    public static String KEY_ID = "id";
    public static String KEY_NAMA = "nama";
    public static String KEY_HARGA = "harga";
    public static String KEY_URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        tvId = findViewById(R.id.tv_id);
        edtUpdateNama = findViewById(R.id.edt_nama);
        edtUpdateHarga = findViewById(R.id.edt_harga);
        edtUpdateUrl = findViewById(R.id.edt_url);
        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_delete);

        edtUpdateNama.setText(getIntent().getStringExtra(KEY_NAMA));
        edtUpdateHarga.setText(getIntent().getStringExtra(KEY_HARGA));
        edtUpdateUrl.setText(getIntent().getStringExtra(KEY_URL));

        final String id = getIntent().getStringExtra(KEY_ID);
        tvId.setText(id);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nama = edtUpdateNama.getText().toString().trim();
                String harga = edtUpdateHarga.getText().toString().trim();
                String url = edtUpdateUrl.getText().toString().trim();

                if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(harga) || TextUtils.isEmpty(url)) {
                    Toast.makeText(UpdateDeleteActivity.this, "Fill required!", Toast.LENGTH_SHORT).show();
                } else {
                    ConfigRetrofit configRetrofit = new ConfigRetrofit();
                    configRetrofit.service.updateMakanan(id, nama, harga, url).enqueue(new Callback<ResponseFood>() {
                        @Override
                        public void onResponse(Call<ResponseFood> call, Response<ResponseFood> response) {
                            if (response.isSuccessful()) {
                                String pesan = response.body().getPesan();
                                boolean sukses = response.body().isSukses();

                                if (sukses) {
                                    Toast.makeText(UpdateDeleteActivity.this, pesan, Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(UpdateDeleteActivity.this, pesan, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseFood> call, Throwable t) {

                        }
                    });
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfigRetrofit configRetrofit = new ConfigRetrofit();
                configRetrofit.service.deleteMakanan(id).enqueue(new Callback<ResponseFood>() {
                    @Override
                    public void onResponse(Call<ResponseFood> call, Response<ResponseFood> response) {
                        if (response.isSuccessful()) {
                            String pesan = response.body().getPesan();
                            boolean status = response.body().isSukses();

                            if (status) {
                                Toast.makeText(UpdateDeleteActivity.this, pesan, Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(UpdateDeleteActivity.this, pesan, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseFood> call, Throwable t) {

                    }
                });
            }
        });
    }
}