package com.imastudio.crudfood;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.imastudio.crudfood.model.DataItem;
import com.imastudio.crudfood.model.ResponseFood;
import com.imastudio.crudfood.network.ConfigRetrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvFood = findViewById(R.id.rv_food);
        rvFood.setLayoutManager(new LinearLayoutManager(this));
        rvFood.setHasFixedSize(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, InsertActivity.class));
            }
        });

        ConfigRetrofit configRetrofit = new ConfigRetrofit();
        configRetrofit.service.getMakanan().enqueue(new Callback<ResponseFood>() {
            @Override
            public void onResponse(Call<ResponseFood> call, Response<ResponseFood> response) {
                if (response.isSuccessful()) {
                    List<DataItem> dataItems = response.body().getData();
                    FoodAdapter adapter = new FoodAdapter(MainActivity.this, dataItems);
                    adapter.notifyDataSetChanged();

                    rvFood.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseFood> call, Throwable t) {

            }
        });

        String email = Preference.getEmail(MainActivity.this);
        String password = Preference.getPassword(MainActivity.this);

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        reloadData();
    }

    private void reloadData() {
        ConfigRetrofit configRetrofit = new ConfigRetrofit();
        configRetrofit.service.getMakanan().enqueue(new Callback<ResponseFood>() {
            @Override
            public void onResponse(Call<ResponseFood> call, Response<ResponseFood> response) {
                if (response.isSuccessful()) {

                    List<DataItem> dataItems = response.body().getData();
                    FoodAdapter adapter = new FoodAdapter(MainActivity.this, dataItems);
                    adapter.notifyDataSetChanged();

                    rvFood.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseFood> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        reloadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {

            Preference.actionLogout(MainActivity.this);
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}
