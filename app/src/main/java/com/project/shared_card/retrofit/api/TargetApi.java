package com.project.shared_card.retrofit.api;

import com.project.shared_card.retrofit.model.Target;
import com.project.shared_card.retrofit.model.dto.HistoryTarget;
import com.project.shared_card.retrofit.model.dto.ProductTarget;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TargetApi {

    @GET("/target/get_all_active/{id}")
    Call<List<ProductTarget>> getAllProduct(@Path("id") Long id);

    @GET("/target/get_all_history/{id}")
    Call<List<HistoryTarget>> getAllHistory(@Path("id") Long id);

    @POST("/target/save_product/{id}")
    Call<Integer> save(@Path("id") Long id, @Body Target target);

    @PUT("/target/update_product/{id}")
    Call<Target> updateProduct(@Path("id") Long id, @Body Target target);

    @PUT("/target/delete_product/{id}")
    Call<Target> deleteProduct(@Path("id") Long id, @Body Target target);
}
