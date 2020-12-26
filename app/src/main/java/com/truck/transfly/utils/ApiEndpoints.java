package com.truck.transfly.utils;

import com.truck.transfly.Model.ResponseAreaManager;
import com.truck.transfly.Model.ResponseFieldStaff;
import com.truck.transfly.Model.ResponseTransporter;
import com.truck.transfly.Model.ResponseVehicleOwner;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

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
}
