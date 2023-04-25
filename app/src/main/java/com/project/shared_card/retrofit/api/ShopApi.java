package com.project.shared_card.retrofit.api;


import com.project.shared_card.retrofit.model.Shop;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ShopApi {
    @GET("/shops/get_all")
    Call<List<Shop>> getAllShop();

    @GET("/shops/get_count")
    Call<Long> getCountShop();

    @GET("/shops/{id}")
    Call<Shop> getShopById(@Path("id") Integer id);

    @POST("/shops/save")
    Call<Shop> save(@Body Shop shop);
}
