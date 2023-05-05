package com.project.shared_card.retrofit.api;

import com.project.shared_card.retrofit.model.Currency;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CurrencyApi {
    @GET("/currency/get_all")
    Call<List<Currency>> getAllCurrencies();

    @GET("/currency/get_count")
    Call<Long> getCountCurrencies();

    @GET("/currency/{id}")
    Call<Currency> getCurrencyById(@Path("id") Integer id);

    @POST("/currency/save")
    Call<Currency> save(@Body Currency currency);
}
