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

    private void getAllMineVehicleOwner(String token)
    {
        api.getAllMineVehicleOwner(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200)
                {

                    ArrayList<ResponseMine> mines  =  new ArrayList<>();

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
                        //for area name
                        HashMap<String , RequestArea> areas = new HashMap<>();
                        //for destination
                        Set<String> loadings = new HashSet<>();
                        Set<RequestArea> areass = new HashSet<>();
                        for(ResponseMine mine: mines) {
                            areas.put(mine.getArea(), new RequestArea(mine.getArea(),mine.getArealatitude(), mine.getArealongitude()));
                            for(String loading: mine.getLoading())
                            {
                                loadings.add(loading);
                            }
                        }

                        for(Map.Entry<String, RequestArea> a: areas.entrySet())
                        {
                            areass.add(a.getValue());
                        }

                        ArrayList<String> loadinglist = new ArrayList<>(loadings);
                        ArrayList<RequestArea> arealist = new ArrayList<>(areass);

                    }

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
    ArrayList<ResponseMine> mines  =  new ArrayList<>();

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


    private void getAllVehicles(String token)
    {
        api.getAllVehicles(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200)
                {
                    ArrayList<ResponseVehicle> vehicleList  =  new ArrayList<>();
                    Type collectionType = new TypeToken<ArrayList<ResponseVehicle>>(){}.getType();
                    try {
                        vehicleList.addAll(new Gson().fromJson(response.body().string().toString(),collectionType));
                    } catch (IOException e) {

                    }
                    if(vehicleList.isEmpty())
                    {

                        Log.d("minal","no vehicle");
                    }
                    else
                    {
                        //['pan','aadhaar','bank']

                        Log.d("minal",vehicleList.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void createBooking(String token, RequestBooking booking)
    {
        api.createBooking(token,booking).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200)
                {
                    Log.d("minal","booking created");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void getAllBookingVehicleOwner(String token)
    {
        api.getBookingVehicleOwner(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200)
                {
                    ArrayList<ResponseBooking> bookingList  =  new ArrayList<>();
                    Type collectionType = new TypeToken<ArrayList<ResponseBooking>>(){}.getType();
                    try {
                        bookingList.addAll(new Gson().fromJson(response.body().string().toString(),collectionType));
                    } catch (IOException e) {

                    }
                    if(bookingList.isEmpty())
                    {

                        Log.d("minal","no vehicle");
                    }
                    else
                    {
                        //['pan','aadhaar','bank']

                        Log.d("minal",bookingList.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private void deleteBooking(String token,int id)
    {
        api.deleteBooking(token,id).enqueue(new Callback<ResponseBooking>() {
            @Override
            public void onResponse(Call<ResponseBooking> call, Response<ResponseBooking> response) {
                if(response.code() == 200)
                {
                    //booking deleted
                }
            }

            @Override
            public void onFailure(Call<ResponseBooking> call, Throwable t) {

            }
        });
    }

    private void createTicket(String token, RequestTicket ticket)
    {
        api.createTicket(token, ticket).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200)
                {
                    //ticket created
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private void createRating(String token, RequestRating rating)
    {
        api.createRating(token, rating).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200)
                {
                    //rating done
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void getAllBookingFieldStaff(String token)
    {
        api.getBookingFieldStaff(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200)
                {
                    ArrayList<ResponseBooking> bookingList  =  new ArrayList<>();
                    Type collectionType = new TypeToken<ArrayList<ResponseBooking>>(){}.getType();
                    try {
                        bookingList.addAll(new Gson().fromJson(response.body().string().toString(),collectionType));
                    } catch (IOException e) {

                    }
                    if(bookingList.isEmpty())
                    {

                        Log.d("minal","no vehicle");
                    }
                    else
                    {
                        //['pan','aadhaar','bank']

                        Log.d("minal",bookingList.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void getAllBookingAreaManager(String token)
    {
        api.getBookingAreaManager(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200)
                {
                    ArrayList<ResponseBooking> bookingList  =  new ArrayList<>();
                    Type collectionType = new TypeToken<ArrayList<ResponseBooking>>(){}.getType();
                    try {
                        bookingList.addAll(new Gson().fromJson(response.body().string().toString(),collectionType));
                    } catch (IOException e) {

                    }
                    if(bookingList.isEmpty())
                    {

                        Log.d("minal","no vehicle");
                    }
                    else
                    {
                        //['pan','aadhaar','bank']

                        Log.d("minal",bookingList.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void confirmBooking(String token, RequestInvoice invoice)
    {
        api.confirmBooking(token,invoice).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200)
                {
                    //booking confirmed
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private void getVehicleFieldStaff(String token,String mobileofvehicleowner)
    {
        api.getVehicleFieldStaff(token,mobileofvehicleowner).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200)
                {
                    ArrayList<String> vehicleList  =  new ArrayList<>();
                    Type collectionType = new TypeToken<ArrayList<String>>(){}.getType();
                    try {
                        vehicleList.addAll(new Gson().fromJson(response.body().string().toString(),collectionType));
                    } catch (IOException e) {

                    }
                    if(vehicleList.isEmpty())
                    {

                        //no vehicle
                        //show booking vehicle
                    }
                    else
                    {
                       //list of all vehicle


                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void onLogin(RequestCredentials credentials)
    {
        api.login(credentials).enqueue(new Callback<ResponseToken>() {
            @Override
            public void onResponse(Call<ResponseToken> call, Response<ResponseToken> response) {
                if(response.code() == 200)
                {
                    ResponseToken tokenobj = response.body();
                    String token  = tokenobj.getToken();
                }
            }

            @Override
            public void onFailure(Call<ResponseToken> call, Throwable t) {

            }
        });
    }


    private void onTransporterList(String token)
    {
        api.getTransporterList(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200)
                {
                    ArrayList<ResponseTransporter> transportersList  =  new ArrayList<>();
                    Type collectionType = new TypeToken<ArrayList<ResponseTransporter>>(){}.getType();
                    try {
                        transportersList.addAll(new Gson().fromJson(response.body().string().toString(),collectionType));
                    } catch (IOException e) {

                    }
                    if(transportersList.isEmpty())
                    {

                        Log.d("minal","no vehicle");
                    }
                    else
                    {
                        //['pan','aadhaar','bank']

                        Log.d("minal",transportersList.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private void getInvoiceVehicleowner(String token)
    {
        api.getInvoiceVehicleOwner(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200)
                {
                    ArrayList<ResponseInvoice> invoices = new ArrayList<>();
                    Type collectionType = new TypeToken<ArrayList<ResponseInvoice>>(){}.getType();
                    try {
                        invoices.addAll(new Gson().fromJson(response.body().string().toString(),collectionType));
                    } catch (IOException e) {

                    }
                    if(invoices.isEmpty())
                    {

                        Log.d("minal","no vehicle");
                    }
                    else
                    {
                        //['pan','aadhaar','bank']

                        Log.d("minal",invoices.toString());
                    }


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private void getInvoiceAreaManager(String token)
    {
        api.getInvoiceAreaManager(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200)
                {
                    ArrayList<ResponseInvoice> invoices = new ArrayList<>();
                    Type collectionType = new TypeToken<ArrayList<ResponseInvoice>>(){}.getType();
                    try {
                        invoices.addAll(new Gson().fromJson(response.body().string().toString(),collectionType));
                    } catch (IOException e) {

                    }
                    if(invoices.isEmpty())
                    {

                        Log.d("minal","no vehicle");
                    }
                    else
                    {
                        //['pan','aadhaar','bank']

                        Log.d("minal",invoices.toString());
                    }


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private void getInvoiceTransporter(String token)
    {
        api.getInvoiceTransporter(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200)
                {
                    ArrayList<ResponseInvoice> invoices = new ArrayList<>();
                    Type collectionType = new TypeToken<ArrayList<ResponseInvoice>>(){}.getType();
                    try {
                        invoices.addAll(new Gson().fromJson(response.body().string().toString(),collectionType));
                    } catch (IOException e) {

                    }
                    if(invoices.isEmpty())
                    {

                        Log.d("minal","no vehicle");
                    }
                    else
                    {
                        //['pan','aadhaar','bank']

                        Log.d("minal",invoices.toString());
                    }


                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }



    private void getReward(String token)
    {
        api.getRewardList(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200)
                {
                    ArrayList<ResponseReward> i = new ArrayList<>();
                    Type collectionType = new TypeToken<ArrayList<ResponseReward>>(){}.getType();
                    try {
                        i.addAll(new Gson().fromJson(response.body().string().toString(),collectionType));
                    } catch (IOException e) {

                    }
                    if(i.isEmpty())
                    {

                        Log.d("minal","no vehicle");
                    }
                    else
                    {
                        //['pan','aadhaar','bank']

                        for(ResponseReward reward: i)
                        {
                            reward.setIm("https://transfly-ftr2t.ondigitalocean.app/rewardimage/" + reward.getId());
                        }
                        Log.d("minal",i.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


  private void getReferral(String token)
  {
      api.getReferralList(token).enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

              ArrayList<ResponseReferral> i = new ArrayList<>();
              Type collectionType = new TypeToken<ArrayList<ResponseReferral>>(){}.getType();
              try {
                  i.addAll(new Gson().fromJson(response.body().string().toString(),collectionType));
              } catch (IOException e) {

              }
              if(i.isEmpty())
              {

                  Log.d("minal","no vehicle");
              }
              else
              {
                  //['pan','aadhaar','bank']

                  for(ResponseReferral reward: i)
                  {
                      reward.setImage("https://transfly-ftr2t.ondigitalocean.app/referralimage/" + reward.getId());
                  }
                  Log.d("minal",i.toString());
              }
          }

          @Override
          public void onFailure(Call<ResponseBody> call, Throwable t) {

          }
      });
  }


  private void updateEmail(String token, RequestEmail email)
  {
      api.updateEmail(token,email).enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
              if(response.code() == 200)
              {


              }
              else
              {

              }
          }

          @Override
          public void onFailure(Call<ResponseBody> call, Throwable t) {

          }
      });
  }

//  private void updatePassword(String token, RequestPassword password)
//  {
//      api.updatePassword(token,password).enqueue(new Callback<ResponseBody>() {
//          @Override
//          public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//              if(response.code() == 200)
//              {
//
//              }
//              else
//              {
//
//              }
//          }
//
//          @Override
//          public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//          }
//      });
//  }

    private void changePassword(RequestCredentials credentials)
    {
        api.updatePassword(credentials).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200)
                {
                    //password changed
                }
                else
                {
                    //user not found
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private void getBanners(String token)
    {
        api.getBanners(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200)
                {

                    ArrayList<ResponseBanner> i = new ArrayList<>();
                    Type collectionType = new TypeToken<ArrayList<ResponseBanner>>(){}.getType();
                    try {
                        i.addAll(new Gson().fromJson(response.body().string().toString(),collectionType));
                    } catch (IOException e) {

                    }
                    if(i.isEmpty())
                    {

                        Log.d("minal","no vehicle");
                    }
                    else
                    {
                        //['pan','aadhaar','bank']

                        for(ResponseBanner banner : i)
                        {
                               banner.setImageUrl("https://transfly-ftr2t.ondigitalocean.app/bannerimage/" + banner.getId());
                        }
                        Log.d("minal",i.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void getResaleList(String token)
    {
        api.getAllResaleList(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200)
                {
                    ArrayList<ResponseResale> i = new ArrayList<>();
                    Type collectionType = new TypeToken<ArrayList<ResponseResale>>(){}.getType();
                    try {
                        i.addAll(new Gson().fromJson(response.body().string().toString(),collectionType));
                    } catch (IOException e) {

                    }
                    if(i.isEmpty())
                    {

                        Log.d("minal","no vehicle");
                    }
                    else
                    {

                        for(ResponseResale resale: i)
                        {
                            ArrayList<String> imageList = new ArrayList<>();
                            for(int j =1;j<=resale.getTotalImage();j++)
                            {
                                imageList.add("https://transfly-ftr2t.ondigitalocean.app/resaleimage/" + resale.getId() + "/" + j);
                            }
                            resale.setImageList(imageList);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private void updateEmergencyContact(String token, RequestEmergencyContact contact)
    {
        api.updateEmergencyContact(token,contact).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() ==200)
                {
                    //done
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    private void updateEmergencyContact2(String token, RequestEmergencyContact2 contact)
    {
        api.updateEmergencyContact2(token,contact).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() ==200)
                {
                    //done
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private void contactForResale(String token)
    {
        api.contactResale(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    //done

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }




    private void getNearmeArea(String token, RequestCoordinates coordinates)
    {
        api.nearmeArea(token,coordinates).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code()==200)
                {
                    String areaname  = response.body().toString();


                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }


    private void getSingleMine(String token, String id)
    {
        api.getSingleMine(token,id).enqueue(new Callback<ResponseMine>() {
            @Override
            public void onResponse(Call<ResponseMine> call, Response<ResponseMine> response) {
                if(response.code() == 200)
                {
                    ResponseMine mine  = response.body();
                    //mine.getLatitude()
                    //mine.getLongitude()
                }
            }

            @Override
            public void onFailure(Call<ResponseMine> call, Throwable t) {

            }
        });
    }


}