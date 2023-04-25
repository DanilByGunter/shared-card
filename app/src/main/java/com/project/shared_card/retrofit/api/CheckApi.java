package com.project.shared_card.retrofit.api;

import com.project.shared_card.retrofit.model.Check;
import com.project.shared_card.retrofit.model.dto.HistoryCheck;
import com.project.shared_card.retrofit.model.dto.ProductCheck;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CheckApi {

    @GET("/check/get_all_active/{id}")
    Call<List<ProductCheck>> getAllProduct(@Path("id") Long id);

    @GET("/check/get_all_history/{id}")
    Call<List<HistoryCheck>> getAllHistory(@Path("id") Long id);

    @POST("/check/save_product/{id}")
    Call<Integer> save(@Path("id") Long id, @Body Check check);

    @PUT("/check/update_product/{id}")
    Call<Check> updateProduct(@Path("id") Long id, @Body Check check);

    @PUT("/check/delete_product/{id}")
    Call<Check> delete(@Path("id") Long id, @Body Check check);
}
