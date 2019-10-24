package com.imastudio.crudfood.network;

import com.imastudio.crudfood.model.ResponseFood;
import com.imastudio.crudfood.model_login.ResponseLogin;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    @GET("Api/getMakanan")
    Call<ResponseFood> getMakanan();

    @FormUrlEncoded
    @POST("Api/insertMakanan")
    Call<ResponseFood> insertMakanan(@Field("name") String nama,
                                     @Field("price") String harga,
                                     @Field("gambar") String url);

    @FormUrlEncoded
    @POST("Api/updateMakanan")
    Call<ResponseFood> updateMakanan(@Field("id") String id,
                                     @Field("name") String nama,
                                     @Field("price") String harga,
                                     @Field("gambar") String url);

    @FormUrlEncoded
    @POST("Api/deleteMakanan")
    Call<ResponseFood> deleteMakanan(@Field("id") String id);

    @FormUrlEncoded
    @POST("Api/login")
    Call<ResponseLogin> actionLogin(@Field("email") String email,
                                    @Field("password") String password);

    @FormUrlEncoded
    @POST("Api/register")
    Call<ResponseFood> actionRegister(@Field("name") String nama,
                                      @Field("email") String email,
                                      @Field("hp") String hp,
                                      @Field("password") String password);

    @Multipart
    @POST("Api/insertGambar")
    Call<ResponseFood> uploadGambar(@Part MultipartBody.Part gambar);

}
