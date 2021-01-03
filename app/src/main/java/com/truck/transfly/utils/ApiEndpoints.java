package com.truck.transfly.utils;

import com.truck.transfly.Model.RequestAadhaarDetails;
import com.truck.transfly.Model.RequestBankDetails;
import com.truck.transfly.Model.RequestBooking;
import com.truck.transfly.Model.RequestCoordinates;
import com.truck.transfly.Model.RequestCredentials;
import com.truck.transfly.Model.RequestEmail;
import com.truck.transfly.Model.RequestEmergencyContact;
import com.truck.transfly.Model.RequestEmergencyContact2;
import com.truck.transfly.Model.RequestEmergencyDetails;
import com.truck.transfly.Model.RequestFeedback;
import com.truck.transfly.Model.RequestGstDetails;
import com.truck.transfly.Model.RequestInvoice;
import com.truck.transfly.Model.RequestPanDetails;
import com.truck.transfly.Model.RequestPassword;
import com.truck.transfly.Model.RequestRating;
import com.truck.transfly.Model.RequestStaDetails;
import com.truck.transfly.Model.RequestTdsDetails;
import com.truck.transfly.Model.RequestTicket;
import com.truck.transfly.Model.RequestUser;
import com.truck.transfly.Model.RequestVehicle;
import com.truck.transfly.Model.ResponseAreaManager;
import com.truck.transfly.Model.ResponseBooking;
import com.truck.transfly.Model.ResponseFieldStaff;
import com.truck.transfly.Model.ResponseMine;
import com.truck.transfly.Model.ResponseRefer;
import com.truck.transfly.Model.ResponseReferral;
import com.truck.transfly.Model.ResponseReward;
import com.truck.transfly.Model.ResponseToken;
import com.truck.transfly.Model.ResponseTransporter;
import com.truck.transfly.Model.ResponseVehicleOwner;

import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiEndpoints {
    //api

    //create user apis
    @POST("/vehicleowner")
    Call<ResponseVehicleOwner> createVehicleOwner(@Body RequestUser user);

    @POST("/transporter")
    Call<ResponseTransporter> createTransporter(@Body RequestUser user);

    @POST("/fieldstaff")
    Call<ResponseFieldStaff> createFieldStaff(@Body RequestUser user);

    @POST("/areamanager")
    Call<ResponseAreaManager> createAreaManager(@Body RequestUser user);

    //login
    @POST("/who")
    Call<ResponseToken> login(@Body RequestCredentials credentials);

    //get user by token
    @GET("/vehicleowner/me")
     Call<ResponseVehicleOwner> getVehicleOwner(@Header("Authorization")String token);

    @GET("/transporter/me")
    Call<ResponseTransporter> getTransporter(@Header("Authorization")String token);

    @GET("/areamanager/me")
    Call<ResponseAreaManager> getAreaManager(@Header("Authorization")String token);

    @GET("/fieldstaff/me")
    Call<ResponseFieldStaff> getFieldStaff(@Header("Authorization")String token);



    //getting list of pending documents
    @GET("/me/pending")
    Call<ResponseBody> getPendingList(@Header("Authorization")String token);



    //getting mines
    @GET("/allmine/vehicleowner")
    Call<ResponseBody> getAllMineVehicleOwner(@Header("Authorization")String token);

    //getting vehicles
    @GET("/allvehicle")
    Call<ResponseBody> getAllVehicles(@Header("Authorization")String token);

    //create booking
    @POST("/booking")
    Call<ResponseBody> createBooking(@Header("Authorization")String token,@Body RequestBooking booking);

    //get all booking of vehicleowner
    @GET("/allbooking/vehicleowner")
    Call<ResponseBody> getBookingVehicleOwner(@Header("Authorization")String token);

    //get all booking of fieldstaff
    @GET("/allbooking/fieldstaff")
    Call<ResponseBody> getBookingFieldStaff(@Header("Authorization")String token);

    //get all booking of areamanager
    @GET("/allbooking/areamanager")
    Call<ResponseBody> getBookingAreaManager(@Header("Authorization")String token);

    //delete booking
    @DELETE("/booking/{id}")
    Call<ResponseBooking> deleteBooking(@Header("Authorization")String token, @Path("id") int id);

    @POST("/ticket")
    Call<ResponseBody> createTicket(@Header("Authorization")String token, @Body RequestTicket ticket);

    @POST("/rating")
    Call<ResponseBody> createRating(@Header("Authorization")String token, @Body RequestRating rating);


    //create invoice
    @POST("/invoice")
    Call<ResponseBody> confirmBooking(@Header("Authorization")String token, @Body RequestInvoice invoice);


    //get list of all vehicle in single booking
    @GET("/booking/vehicle/{mobile}")
    Call<ResponseBody> getVehicleFieldStaff(@Header("Authorization")String token, @Path("mobile") String mobile);

    //get list of all transporters in single booking
    @GET("/alltransporter")
    Call<ResponseBody> getTransporterList(@Header("Authorization")String token);


    //get  invoice of vehicleowner
    @GET("/allinvoice/vehicleowner")
    Call<ResponseBody> getInvoiceVehicleOwner(@Header("Authorization")String token);

    //get  invoice of areamanager
    @GET("/allinvoice/areamanager")
    Call<ResponseBody> getInvoiceAreaManager(@Header("Authorization")String token);

    //get  invoice of vehicleowner
    @GET("/allinvoice/transporter")
    Call<ResponseBody> getInvoiceTransporter(@Header("Authorization")String token);


    //get reward
    @GET("/allreward")
    Call<ResponseBody> getRewardList(@Header("Authorization")String token);

    //get referral
    @GET("/allreferral")
    Call<ResponseBody> getReferralList(@Header("Authorization")String token);


    //update email
    @POST("/me/update")
    Call<ResponseBody> updateEmail(@Header("Authorization")String token, @Body RequestEmail email);

    //update emergencycontact
    @POST("/me/update")
    Call<ResponseBody> updateEmergencyContact(@Header("Authorization")String token, @Body RequestEmergencyContact emergencyContact);


    //update emergencycontact
    @POST("/me/update")
    Call<ResponseBody> updateEmergencyContact2(@Header("Authorization")String token, @Body RequestEmergencyContact2 emergencyContact);


    //update password
    @POST("/changepassword")
    Call<ResponseBody> updatePassword( @Body RequestCredentials credentials);


    //get banner
    @GET("/allbanner")
    Call<ResponseBody> getBanners(@Header("Authorization")String token);

    @GET("/allresale")
    Call<ResponseBody>  getAllResaleList(@Header("Authorization")String token);


    @POST("/resale/contact")
    Call<ResponseBody> contactResale(@Header("Authorization")String token);



    @POST("/nearme/mine")
    Call<String> nearmeArea(@Header("Authorization")String token, @Body RequestCoordinates coordinates);


    @GET("/mine/{id}")
    Call<ResponseMine> getSingleMine(@Header("Authorization")String token,@Path("id") String id);


    ///////////////////////////////////////

    @POST("/vehicleowner/login")
    Call<ResponseVehicleOwner> vehicleOwnerLogin(@Body RequestCredentials credentials);

    @POST("/areamanager/login")
    Call<ResponseAreaManager> areaManagerLogin(@Body RequestCredentials credentials);

    @POST("/transporter/login")
    Call<ResponseTransporter> transporterLogin(@Body RequestCredentials credentials);

    @POST("/fieldstaff/login")
    Call<ResponseFieldStaff> fieldStaffLogin(@Body RequestCredentials credentials);

    @PATCH("/vehicleowner/me")
    Call<ResponseBody> aadhaarUpdateVehicleOwner(@Header("Authorization")String token,@Body RequestAadhaarDetails aadhaarDetails);

    @PATCH("/areamanager/me")
    Call<ResponseBody> aadhaarUpdateAreaManager(@Header("Authorization")String token,@Body RequestAadhaarDetails aadhaarDetails);

    @PATCH("/transporter/me")
    Call<ResponseBody> aadhaarUpdateTransporter(@Header("Authorization")String token,@Body RequestAadhaarDetails aadhaarDetails);

    @PATCH("/fieldstaff/me")
    Call<ResponseBody> aadhaarUpdateFieldStaff(@Header("Authorization")String token,@Body RequestAadhaarDetails aadhaarDetails);

    @PATCH("/vehicleowner/me")
    Call<ResponseBody> panUpdateVehicleOwner(@Header("Authorization")String token,@Body RequestPanDetails panDetails);

    @PATCH("/areamanager/me")
    Call<ResponseBody> panUpdateAreaManager(@Header("Authorization")String token,@Body RequestPanDetails panDetails);

    @PATCH("/transporter/me")
    Call<ResponseBody> panUpdateTransporter(@Header("Authorization")String token,@Body RequestPanDetails panDetails);

    @PATCH("/fieldstaff/me")
    Call<ResponseBody> panUpdateFieldStaff(@Header("Authorization")String token,@Body RequestPanDetails panDetails);

    @PATCH("/vehicleowner/me")
    Call<ResponseBody> bankUpdateVehicleOwner(@Header("Authorization")String token,@Body RequestBankDetails bankDetails);

    @PATCH("/areamanager/me")
    Call<ResponseBody> bankUpdateAreaManager(@Header("Authorization")String token,@Body RequestBankDetails bankDetails);

    @PATCH("/transporter/me")
    Call<ResponseBody> bankUpdateTransporter(@Header("Authorization")String token,@Body RequestBankDetails bankDetails);

    @PATCH("/fieldstaff/me")
    Call<ResponseBody> bankUpdateFieldStaff(@Header("Authorization")String token,@Body RequestBankDetails bankDetails);

    @PATCH("/vehicleowner/me")
    Call<ResponseBody> emergencyContactUpdateVehicleOwner(@Header("Authorization")String token,@Body RequestEmergencyDetails emergencyContactDetails);

    @PATCH("/areamanager/me")
    Call<ResponseBody> emergencyContactUpdateAreaManager(@Header("Authorization")String token,@Body RequestEmergencyDetails emergencyContactDetails);

    @PATCH("/transporter/me")
    Call<ResponseBody> emergencyContactUpdateTransporter(@Header("Authorization")String token,@Body RequestEmergencyDetails emergencyContactDetails);

    @PATCH("/fieldstaff/me")
    Call<ResponseBody> emergencyContactUpdateFieldStaff(@Header("Authorization")String token,@Body RequestEmergencyDetails emergencyContactDetails);

    @PATCH("/vehicleowner/me")
    Call<ResponseBody> gstUpdateVehicleOwner(@Header("Authorization")String token,@Body RequestGstDetails gstDetails);

    @PATCH("/areamanager/me")
    Call<ResponseBody> gstUpdateAreaManager(@Header("Authorization")String token,@Body RequestGstDetails gstDetails);

    @PATCH("/transporter/me")
    Call<ResponseBody> gstUpdateTransporter(@Header("Authorization")String token,@Body RequestGstDetails gstDetails);

    @PATCH("/fieldstaff/me")
    Call<ResponseBody> gstUpdateFieldStaff(@Header("Authorization")String token,@Body RequestGstDetails gstDetails);


    @PATCH("/vehicleowner/me")
    Call<ResponseBody> staUpdateVehicleOwner(@Header("Authorization")String token,@Body RequestStaDetails staDetails);

    @PATCH("/areamanager/me")
    Call<ResponseBody> staUpdateAreaManager(@Header("Authorization")String token,@Body RequestStaDetails staDetails);

    @PATCH("/transporter/me")
    Call<ResponseBody> staUpdateTransporter(@Header("Authorization")String token,@Body RequestStaDetails staDetails);

    @PATCH("/fieldstaff/me")
    Call<ResponseBody> staUpdateFieldStaff(@Header("Authorization")String token,@Body RequestStaDetails staDetails);

    @PATCH("/vehicleowner/me")
    Call<ResponseBody> tdsUpdateVehicleOwner(@Header("Authorization")String token,@Body RequestTdsDetails tdsDetails);

    @POST("/vehicleowner")
    Call<ResponseBody> createVehicleOwner(@Header("Authorization")String token,@Body RequestUser user);

    @POST("/areamanager")
    Call<ResponseBody> createAreaManager(@Header("Authorization")String token,@Body RequestUser user);

    @POST("/transporter")
    Call<ResponseBody> createTransporter(@Header("Authorization")String token,@Body RequestUser user);

    @POST("/fieldstaff")
    Call<ResponseBody> createFieldStaff(@Header("Authorization")String token,@Body RequestUser user);

//    @POST("/booking")
//    Call<ResponseBody> createBooking(@Header("Authorization")String token,@Body RequestBooking booking);

    @POST("/feedback")
    Call<ResponseBody> createFeedback(@Header("Authorization")String token,@Body RequestFeedback feedback);

    @PATCH("/booking/{id}")
    Call<ResponseBody> updateBooking(@Header("Authorization")String token, @Body RequestInvoice invoice, @Path("id") int id);

    @GET("/booking/{id}")
    Call<ResponseBooking> getSingleBooking(@Header("Authorization")String token, @Path("id") int id);

    @GET("/invoice/{id}")
    Call<ResponseBooking> getSingleInvoice(@Header("Authorization")String token, @Path("id") int id);

    @GET("/invoice/me")
    Call<ResponseBody> getVehicleOwnerInvoice(@Header("Authorization")String token);

    @GET("/booking/me")
    Call<ResponseBody> getVehicleOwnerBooking(@Header("Authorization")String token);

    @POST("/vehicle")
    Call<ResponseBody> addVehicle(@Header("Authorization")String token, @Body RequestVehicle vehicle);

    @GET("/vehicle/me")
    Call<ResponseBody> getVehicleOwnerVehicle(@Header("Authorization")String token);


















}
