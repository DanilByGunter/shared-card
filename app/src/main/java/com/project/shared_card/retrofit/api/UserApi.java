package com.project.shared_card.retrofit.api;

import com.project.shared_card.retrofit.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserApi {
    @GET("/user/last_id")
    Call<Long> getLastUserId();
    @POST("/user/save")
    Call<Long> addUser(@Body User user);
}
