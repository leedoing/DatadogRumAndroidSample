package com.example.datadogrumandroidsample;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitAPI {

   @GET("price.json")
   Call<HyunjinResponse> test();

}