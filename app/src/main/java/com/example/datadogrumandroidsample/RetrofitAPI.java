package com.example.datadogrumandroidsample;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitAPI {

   @GET("manifest.json")
   Call<HyunjinResponse> test();

}