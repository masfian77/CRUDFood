package com.imastudio.crudfood.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConfigRetrofit {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://172.20.30.38/server_resto_ios/index.php/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public ApiService service = retrofit.create(ApiService.class);

}
