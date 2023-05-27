package com.project.shared_card.retrofit.api;

import com.project.shared_card.retrofit.model.TheAllGroup;
import com.project.shared_card.retrofit.model.dto.TheAllGroupWithUser;
import com.project.shared_card.retrofit.model.dto.UserWithGroup;
import com.project.shared_card.retrofit.model.dto.UsersGroup;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface GroupIdApi {
    @POST("/group/get_all")
    Call<TheAllGroupWithUser> getAllUsers(@Body UserWithGroup user);

    @POST("/group/get_all_info")
    Call<TheAllGroupWithUser> getAllUsersInGroup(@Body UserWithGroup user);

    @POST("/group/save_user/{id}")
    Call<TheAllGroup> saveUser(@Path("id") Long id);

    @PUT("/group/delete_user/{id}")
    Call<TheAllGroup> deleteUser(@Path("id") Long id);
}
