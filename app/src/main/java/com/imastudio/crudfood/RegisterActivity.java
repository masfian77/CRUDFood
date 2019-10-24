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

public class RegisterActivity extends AppCompatActivity {

    EditText edtName, edtEmail, edtPhone, edtPassword;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtPhone = findViewById(R.id.edt_phone);
        edtPassword = findViewById(R.id.edt_password);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nama = edtName.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String hp = edtPhone.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (TextUtils.isEmpty(nama) || TextUtils.isEmpty(email) || TextUtils.isEmpty(hp) || TextUtils.isEmpty(password)) {
                    Toast.makeText(RegisterActivity.this, "Fill required!", Toast.LENGTH_SHORT).show();
                }

                ConfigRetrofit configRetrofit = new ConfigRetrofit();
                configRetrofit.service.actionRegister(nama,email,hp,password).enqueue(new Callback<ResponseFood>() {
                    @Override
                    public void onResponse(Call<ResponseFood> call, Response<ResponseFood> response) {

                        if (response.isSuccessful()) {
                            String pesan = response.body().getPesan();
                            boolean sukses = response.body().isSukses();

                            if (sukses){
                                Toast.makeText(RegisterActivity.this, pesan, Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(RegisterActivity.this, pesan, Toast.LENGTH_SHORT).show();
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
