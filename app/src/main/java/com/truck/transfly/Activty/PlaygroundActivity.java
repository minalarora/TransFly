package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.truck.transfly.R;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import com.truck.transfly.Model.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.truck.transfly.R;

public class PlaygroundActivity extends AppCompatActivity {

    private  Retrofit retrofit = null;
    private  ApiEndpoints api = null;
    private  String token = "vehicleowner:eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiIxMTExMTExMSIsImlhdCI6MTYwOTE3OTgyOSwiZXhwIjoxNjExNzcxODI5fQ.YUibiAIPlx8L5VtRFbpPNtjWP0oNLg-91aPE64elLq8";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);
        retrofit = ApiClient.getRetrofitClient();
        if(retrofit!=null)
        {
            api = retrofit.create(ApiEndpoints.class);
        }

       createVehicleOwner(new RequestUser("Minal Arora","minalvehicleowner@gmail.com","11111111","password"));
        //createFieldStaff(new RequestUser("Minal Arora","minalfieldstaff@gmail.com","2222222","password"));
       // createTransporter(new RequestUser("Minal Arora","minaltransporter@gmail.com","333333333","password"));
        //createAreaManager(new RequestUser("Minal Arora","minalareamanager@gmail.com","44444444","password"));


       // getVehicleOwner(token);
       // getAreaManager(token);
        //getTransporter(token);
        //getFieldStaff(token);


       // getPendingList(token);




    }

    private  void createVehicleOwner(RequestUser user)
    {


            api.createVehicleOwner(user).enqueue(new Callback<ResponseVehicleOwner>() {
                @Override
                public void onResponse(Call<ResponseVehicleOwner> call, Response<ResponseVehicleOwner> response) {
                    if(response.code() == 200)
                    {
                        ResponseVehicleOwner vehicleOwner = response.body();
                        //line 1
                       Log.d("minal",vehicleOwner.toString());

                    }
                    else
                    {
                        //user create failed
                    }
                }

                @Override
                public void onFailure(Call<ResponseVehicleOwner> call, Throwable t) {
                    //no internet connection
                }
            });
    }

    private  void createFieldStaff(RequestUser user)
    {


        api.createFieldStaff(user).enqueue(new Callback<ResponseFieldStaff>() {
            @Override
            public void onResponse(Call<ResponseFieldStaff> call, Response<ResponseFieldStaff> response) {
                if(response.code() == 200)
                {
                    ResponseFieldStaff fieldStaff = response.body();
                    Log.d("minal",fieldStaff.toString());

                }
                else
                {

                }
            }

            @Override
            public void onFailure(Call<ResponseFieldStaff> call, Throwable t) {

            }
        });
    }

    private  void createTransporter(RequestUser user)
    {


       api.createTransporter(user).enqueue(new Callback<ResponseTransporter>() {
           @Override
           public void onResponse(Call<ResponseTransporter> call, Response<ResponseTransporter> response) {
               if(response.code() == 200)
               {
                   ResponseTransporter transporter = response.body();
                   Log.d("minal",transporter.toString());
               }
               else
               {

               }
           }

           @Override
           public void onFailure(Call<ResponseTransporter> call, Throwable t) {

           }
       });
    }

    private  void createAreaManager(RequestUser user)
    {
            api.createAreaManager(user).enqueue(new Callback<ResponseAreaManager>() {
                @Override
                public void onResponse(Call<ResponseAreaManager> call, Response<ResponseAreaManager> response) {
                    if(response.code() == 200)
                    {
                        ResponseAreaManager areaManager = response.body();
                        Log.d("minal",areaManager.toString());
                    }
                    else
                    {

                    }
                }

                @Override
                public void onFailure(Call<ResponseAreaManager> call, Throwable t) {

                }
            });


    }

    private void getVehicleOwner(String token)
    {
        api.getVehicleOwner(token).enqueue(new Callback<ResponseVehicleOwner>() {
            @Override
            public void onResponse(Call<ResponseVehicleOwner> call, Response<ResponseVehicleOwner> response) {
                if(response.code() == 200)
                {
                    ResponseVehicleOwner vehicleOwner = response.body();
                    Log.d("minal",vehicleOwner.toString());
                }
                else
                {

                }
            }

            @Override
            public void onFailure(Call<ResponseVehicleOwner> call, Throwable t) {

            }
        });
    }

    private void getFieldStaff(String token)
    {
        api.getFieldStaff(token).enqueue(new Callback<ResponseFieldStaff>() {
            @Override
            public void onResponse(Call<ResponseFieldStaff> call, Response<ResponseFieldStaff> response) {
                if(response.code() == 200)
                {
                    ResponseFieldStaff fieldStaff = response.body();
                    Log.d("minal",fieldStaff.toString());
                }
                else
                {

                }
            }

            @Override
            public void onFailure(Call<ResponseFieldStaff> call, Throwable t) {

            }
        });
    }

    private void getTransporter(String token)
    {
        api.getTransporter(token).enqueue(new Callback<ResponseTransporter>() {
            @Override
            public void onResponse(Call<ResponseTransporter> call, Response<ResponseTransporter> response) {
                if(response.code() == 200)
                {
                    ResponseTransporter transporter = response.body();
                    Log.d("minal",transporter.toString());
                }
                else
                {

                }
            }

            @Override
            public void onFailure(Call<ResponseTransporter> call, Throwable t) {

            }
        });
    }

    private void getAreaManager(String token)
    {
        api.getAreaManager(token).enqueue(new Callback<ResponseAreaManager>() {
            @Override
            public void onResponse(Call<ResponseAreaManager> call, Response<ResponseAreaManager> response) {
                if(response.code() == 200)
                {
                    ResponseAreaManager areaManager = response.body();
                    Log.d("minal",areaManager.toString());
                }
                else
                {

                }
            }

            @Override
            public void onFailure(Call<ResponseAreaManager> call, Throwable t) {

            }
        });
    }

    private void getPendingList(String token)
    {
        api.getPendingList(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200)
                {

                    ArrayList<String> pendingList  =  new ArrayList<>();
                    Type collectionType = new TypeToken<ArrayList<String>>(){}.getType();
                    try {
                        pendingList.addAll(new Gson().fromJson(response.body().string().toString(),collectionType));
                    } catch (IOException e) {

                    }
                    if(pendingList.isEmpty())
                    {

                        Log.d("minal","kyc completed");
                    }
                    else
                    {
                        //['pan','aadhaar','bank']

                        Log.d("minal",pendingList.toString());
                    }


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    //top level
    ArrayList<ResponseMine> mines  =  new ArrayList<>();
    private void getAllMineVehicleOwner(String token)
    {
        api.getAllMineVehicleOwner(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200)
                {

                    mines.clear();
                    Type collectionType = new TypeToken<ArrayList<ResponseMine>>(){}.getType();
                    try {
                        mines.addAll(new Gson().fromJson(response.body().string().toString(),collectionType));
                    } catch (IOException e) {

                    }
                    if(mines.isEmpty())
                    {
                        Log.d("minal","mine not found");
                    }
                    else
                    {
                        Log.d("minal",mines.toString());

//                        //for area name
//                        Set<RequestArea> areas = new HashSet<>();
//                        //for destination
//                        Set<String> loadings = new HashSet<String>();
//                        for(ResponseMine mine: mines) {
//                            areas.put(mine.getArea(), new RequestArea(mine.getArealatitude(), mine.getArealongitude()));
//                            for(String loading: mine.getLoading())
//                            {
//                                loadings.add(loading);
//                            }
//                        }
//
//                        //for shani
//                        for(Map.Entry<String, RequestArea> a: areas.entrySet())
//                        {
//                            Log.d("minal","area=" + a.getKey() + "lat = " + a.getValue().getArealatitude() + "long = " + a.getValue().getArealongitude() );
//
//                        }

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private ArrayList<ResponseMine> getAllMineOfSingleArea(String area,String loading)
    {
        ArrayList<ResponseMine> selectedmines = new ArrayList<>();
        for(ResponseMine m: mines)
        {
            if(m.getArea().equalsIgnoreCase(area))
            {
                for(String l: m.getLoading())
                {
                    if(l.equalsIgnoreCase(loading))
                    {
                        selectedmines.add(m);
                    }
                }
            }
        }
        return  mines;
    }





}