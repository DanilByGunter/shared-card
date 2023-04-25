package com.project.shared_card.retrofit.api;

import com.project.shared_card.retrofit.model.Metric;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MetricApi {
    @GET("/metric/get_all")
    Call<List<Metric>> getAllMetric();

    @GET("/metric/get_count")
    Call<Long> getCountMetric();

    @GET("/metric/{id}")
    Call<Metric> getMetricById(@Path("id") Integer id);

    @POST("/metric/save")
    Call<Metric> save(@Body Metric metric);
}
