package com.shahin.drivesafe.Robi;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface APIInterface {


    @Headers({"Accept: application/json"})
    @GET("api/sub_check")
    Call<Status> getStatus(@Query("code") String code);


}


