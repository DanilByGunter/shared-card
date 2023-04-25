package com.project.shared_card.retrofit.api;

import com.project.shared_card.retrofit.model.Category;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CategoryApi {
    @GET("/category/get_all")
    Call<List<Category>> getAllCategory();

    @GET("/category/get_count")
    Call<Long> getCountCategory();

    @GET("/category/{id}")
    Call<Category> getCategoryById(@Path("id") Integer id);

    @POST("/category/save")
    Call<Category> save(@Body Category category);
}
