package com.truck.transfly.Activty;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.truck.transfly.Adapter.MyVehicleAdapter;
import com.truck.transfly.Frament.MobileUpdateDailogFragment;
import com.truck.transfly.Frament.OtpDialogFragment;
import com.truck.transfly.Frament.RemoveVehcileDialog;
import com.truck.transfly.Model.ResponseAreaManager;
import com.truck.transfly.Model.ResponseVehicle;
import com.truck.transfly.Model.ResponseVehicleOwner;
import com.truck.transfly.Model.UpdateVehicle;
import com.truck.transfly.R;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.EndApi;
import com.truck.transfly.utils.PreferenceUtil;
import com.truck.transfly.utils.TransflyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class MyVehicleActivity extends AppCompatActivity {

    private List<ResponseVehicle> responseVehicleList = new ArrayList<>();
    private MyVehicleAdapter myVehicleAdapter;
    private FrameLayout parent_of_loading;
    private Retrofit retrofit;
    private ApiEndpoints api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vehicle);

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        myVehicleAdapter = new MyVehicleAdapter(MyVehicleActivity.this, responseVehicleList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyVehicleActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(myVehicleAdapter);

        myVehicleAdapter.setOnClickListener(new MyVehicleAdapter.onClickListener() {
            @Override
            public void onClick(ResponseVehicle responseVehicle, int position) {

                RemoveVehcileDialog removeDialogFragment = new RemoveVehcileDialog();
                removeDialogFragment.show(getSupportFragmentManager(), "");
                removeDialogFragment.setCancelable(false);
                removeDialogFragment.setOnClickListener(new RemoveVehcileDialog.onClickListener() {
                    @Override
                    public void onClick() {

                        ResponseVehicleOwner responseVehicleOwner = ((TransflyApplication) getApplication()).getResponseVehicleOwner();

                        OtpDialogFragment otpDialogFragment=new OtpDialogFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("mobileNo", responseVehicleOwner.getMobile());
                        otpDialogFragment.setArguments(bundle);
                        otpDialogFragment.setCancelable(false);
                        otpDialogFragment.show(getSupportFragmentManager(),"otpDialogFragment");

                        otpDialogFragment.setOnClickListener(new OtpDialogFragment.onClickListener() {
                            @Override
                            public void onClick(int i) {

                                if(i==1){

                                    deleteVehicle(PreferenceUtil.getData(MyVehicleActivity.this,"token"),responseVehicle.getId(),position);
                                }

                            }
                        });

                    }
                });

            }

            @Override
            public void onEdit(ResponseVehicle responseVehicle, int position) {

                MobileUpdateDailogFragment mobileUpdateDailogFragment = new MobileUpdateDailogFragment();

                Bundle bundle=new Bundle();
                bundle.putString("number",responseVehicle.getContact());

                mobileUpdateDailogFragment.setArguments(bundle);
                mobileUpdateDailogFragment.setCancelable(false);
                mobileUpdateDailogFragment.show(getSupportFragmentManager(), "mobileUpdate");

                mobileUpdateDailogFragment.setOnClickListener(new MobileUpdateDailogFragment.onClickListener() {
                    @Override
                    public void onClick(String s) {

                        UpdateVehicle updateVehicle=new UpdateVehicle();
                        updateVehicle.setId(responseVehicle.getId());
                        updateVehicle.setContact(s);

                        updateVehicle(PreferenceUtil.getData(MyVehicleActivity.this,"token"),updateVehicle,position);

                    }
                });

            }
        });

        getAllVehicles();

    }

    private void getAllVehicles() {

        parent_of_loading.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, EndApi.MY_VEHICLE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                parent_of_loading.setVisibility(View.GONE);

                try {
                    JSONArray jsonArray = new JSONArray(response);

                    if (jsonArray.length() > 0) {

                        for (int i = 0; i < jsonArray.length(); i++) {

                            ResponseVehicle responseVehicle = new ResponseVehicle();

                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            responseVehicle.setNumber(jsonObject.getString("number"));
                            responseVehicle.setId(jsonObject.getInt("id"));
                            responseVehicle.setContact(jsonObject.getString("contact"));
                            responseVehicle.setRc(jsonObject.getString("rc"));
                            responseVehicle.setVehiclename(jsonObject.getString("vehiclename"));
                            responseVehicle.setStatus(jsonObject.getInt("status"));
                            responseVehicle.setActive(jsonObject.getBoolean("active"));
                            responseVehicle.setTyres(jsonObject.getString("tyres"));
                            responseVehicle.setDate(jsonObject.getString("date"));

                            responseVehicleList.add(responseVehicle);
                        }

                        myVehicleAdapter.notifyDataSetChanged();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                parent_of_loading.setVisibility(View.GONE);

                Toast.makeText(MyVehicleActivity.this, "No Internet Connection ! Try Again", Toast.LENGTH_SHORT).show();

            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> map = new HashMap<>();
                map.put("AUTHORIZATION", PreferenceUtil.getData(MyVehicleActivity.this, "token"));

                return map;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                return map;
            }
        };

        Volley.newRequestQueue(MyVehicleActivity.this).add(stringRequest);

    }


    private void updateVehicle(String token, UpdateVehicle vehicle, int position) {

        parent_of_loading.setVisibility(View.VISIBLE);

        api.updateVehicle(token, vehicle).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if (response.code() == 200) {

                    Toast.makeText(MyVehicleActivity.this, "Vehicle Info Updated Successfully", Toast.LENGTH_SHORT).show();

                    ResponseVehicle responseVehicle = responseVehicleList.get(position);
                    responseVehicle.setContact(vehicle.getContact());
                    myVehicleAdapter.notifyDataSetChanged();

                } else {

                    Toast.makeText(MyVehicleActivity.this, "Something Went wrong! Try Again", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);

                Toast.makeText(MyVehicleActivity.this, "No Internet Connection! Try Again", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void deleteVehicle(String token, int id, int position) {
        parent_of_loading.setVisibility(View.VISIBLE);

        api.deleteVehicle(token, id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if (response.code() == 200) {

                    Toast.makeText(MyVehicleActivity.this, "Vehicle Deleted Successfully", Toast.LENGTH_SHORT).show();

                    responseVehicleList.remove(position);
                    myVehicleAdapter.notifyDataSetChanged();

                } else {

                    Toast.makeText(MyVehicleActivity.this, "Something Went wrong! Try Again", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);

                Toast.makeText(MyVehicleActivity.this, "No Internet Connection! Try Again", Toast.LENGTH_SHORT).show();

            }
        });
    }
}