package com.project.shared_card.retrofit.api;

import com.project.shared_card.retrofit.model.TheAllGroup;
import com.project.shared_card.retrofit.model.TheGroupId;
import com.project.shared_card.retrofit.model.dto.TheAllGroupWithUserId;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TheAllGroupApi {
    @GET("/groups/{id}")
    Call<TheAllGroup> getGroupById(@Path("id") Long id);

    @GET("/groups/last_id")
    Call<Long> getLastId();

    @PUT("/groups/update_name")
    Call<TheAllGroup> updateGroupName(@Body TheAllGroup theAllGroup);

    @PUT("/groups/update_photo")
    Call<TheAllGroup> updateGroupPhoto(@Body TheAllGroup theAllGroup);

    @POST("/groups/save")
    Call<Long> save(@Body TheAllGroup theAllGroup);
}
