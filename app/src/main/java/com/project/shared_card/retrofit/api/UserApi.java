package com.project.shared_card.retrofit.api;

import com.project.shared_card.retrofit.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {
    @GET("/user/last_id")
    Call<Long> getLastUserId();

    @POST("/user/save")
    Call<User> addUser(@Body User user);

    @GET("/user/{id}")
    Call<User> getUserById(@Path("id") Long id, @Body User user);

    @PUT("/user/update_name")
    Call<User> updateUserName(@Body User user);

    @PUT("/user/update_photo")
    Call<User> updateUserPhoto(@Body User user);
}
