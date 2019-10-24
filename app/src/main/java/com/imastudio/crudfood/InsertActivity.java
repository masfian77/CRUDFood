package com.imastudio.crudfood;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.imastudio.crudfood.model.ResponseFood;
import com.imastudio.crudfood.network.ConfigRetrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InsertActivity extends AppCompatActivity {

    EditText edtNama, edtHarga, edtUrl;
    Button btnInsert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        edtNama = findViewById(R.id.edt_nama);
        edtHarga = findViewById(R.id.edt_harga);
        edtUrl = findViewById(R.id.edt_url);
        btnInsert = findViewById(R.id.btn_insert);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nama = edtNama.getText().toString().trim();
                String harga = edtHarga.getText().toString().trim();
                String url = edtUrl.getText().toString().trim();

                if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(harga) || TextUtils.isEmpty(url)) {
                    Toast.makeText(InsertActivity.this, "Fill required!", Toast.LENGTH_SHORT).show();
                }else {
                    ConfigRetrofit configRetrofit = new ConfigRetrofit();
                    configRetrofit.service.insertMakanan(nama, harga, url).enqueue(new Callback<ResponseFood>() {
                        @Override
                        public void onResponse(Call<ResponseFood> call, Response<ResponseFood> response) {
                            if (response.isSuccessful()) {
                                String pesan = response.body().getPesan();
                                boolean sukses = response.body().isSukses();

                                if (sukses) {
                                    Toast.makeText(InsertActivity.this, pesan, Toast.LENGTH_SHORT).show();
                                    finish();
                                }else {
                                    Toast.makeText(InsertActivity.this, pesan, Toast.LENGTH_SHORT).show();
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

    }
}
