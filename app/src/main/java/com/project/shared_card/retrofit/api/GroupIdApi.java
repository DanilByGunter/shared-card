package com.project.shared_card.retrofit.api;

import com.project.shared_card.retrofit.model.TheGroupId;
import com.project.shared_card.retrofit.model.dto.UsersGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GroupIdApi {
    @GET("/group/get_all/{id}")
    Call<List<UsersGroup>> getAllUsers(@Path("id") Long id);

    @POST("/group/save_user/{id}")
    Call<TheGroupId> saveUser(@Path("id") Long id);

    @PUT("/group/delete_user/{id}")
    Call<TheGroupId> deleteUser(@Path("id") Long id);
}
