package com.truck.transfly.utils;

import com.truck.transfly.Model.RequestCredentials;
import com.truck.transfly.Model.ResponseAreaManager;
import com.truck.transfly.Model.ResponseFieldStaff;
import com.truck.transfly.Model.ResponseTransporter;
import com.truck.transfly.Model.ResponseVehicleOwner;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiEndpoints {
    //api


    @GET("/vehicleowner/me")
     Call<ResponseVehicleOwner> getVehicleOwner(@Header("Authorization")String token);

    @GET("/transporter/me")
    Call<ResponseTransporter> getTransporter(@Header("Authorization")String token);

    @GET("/areamanager/me")
    Call<ResponseAreaManager> getAreaManager(@Header("Authorization")String token);

    @GET("/fieldstaff/me")
    Call<ResponseFieldStaff> getFieldStaff(@Header("Authorization")String token);

    @POST("/login")
    Call<ResponseBody> login(@Body RequestCredentials credentials);

    @POST("/vehicleowner/login")
    Call<ResponseVehicleOwner> vehicleOwnerLogin(@Body RequestCredentials credentials);

    @POST("/areamanager/login")
    Call<ResponseAreaManager> areaManagerLogin(@Body RequestCredentials credentials);

    @POST("/transporter/login")
    Call<ResponseTransporter> transporterLogin(@Body RequestCredentials credentials);

    @POST("/fieldstaff/login")
    Call<ResponseFieldStaff> fieldStaffLogin(@Body RequestCredentials credentials);


}
