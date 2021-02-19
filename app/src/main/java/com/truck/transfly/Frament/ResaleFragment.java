package com.truck.transfly.Frament;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.truck.transfly.Activty.OlxPageActivity;
import com.truck.transfly.Adapter.OlxRecyclerAdapter;
import com.truck.transfly.Model.ResponseResale;
import com.truck.transfly.R;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ResaleFragment extends Fragment {

    private List<String> stringList=new ArrayList<>();
    private Retrofit retrofit = null;
    ArrayList<ResponseResale> responseList = new ArrayList<>();
    private ApiEndpoints api = null;
    private OlxRecyclerAdapter olxRecyclerAdapter;
    private FrameLayout parent_of_loading;
    private RelativeLayout no_internet_connection;
    private TextView no_vehicle_found;
    private FragmentActivity fragmentActivity;

    public ResaleFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        fragmentActivity= (FragmentActivity) context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View inflate = inflater.inflate(R.layout.fragment_resale, container, false);

        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        parent_of_loading=inflate.findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        no_vehicle_found = inflate.findViewById(R.id.no_vehicle_found);
        no_vehicle_found.setVisibility(View.GONE);

        EditText searchLease =inflate.findViewById(R.id.search_lease);

        searchLease.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                olxRecyclerAdapter.getFilter().filter(s);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        no_internet_connection = inflate.findViewById(R.id.no_internet_connection);
        inflate.findViewById(R.id.pullToRefresh_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                responseList.clear();
                no_internet_connection.setVisibility(View.GONE);
                olxRecyclerAdapter.notifyDataSetChanged();
                getResaleList(PreferenceUtil.getData(fragmentActivity,"token"));

            }
        });

        RecyclerView recyclerView =inflate.findViewById(R.id.recyclerView);
        olxRecyclerAdapter=new OlxRecyclerAdapter(fragmentActivity,responseList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(fragmentActivity,RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(olxRecyclerAdapter);

        getResaleList(PreferenceUtil.getData(fragmentActivity,"token"));

        return inflate;

    }

    private void getResaleList(String token)
    {
        parent_of_loading.setVisibility(View.VISIBLE);
        no_internet_connection.setVisibility(View.GONE);

        api.getAllResaleList(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if(response.code() == 200)
                {
                    Type collectionType = new TypeToken<ArrayList<ResponseResale>>(){}.getType();
                    try {
                        responseList.addAll(new Gson().fromJson(response.body().string().toString(),collectionType));
                    } catch (IOException e) {

                    }
                    if(responseList.isEmpty())
                    {

                        no_vehicle_found.setVisibility(View.VISIBLE);

                        Log.d("minal","no vehicle");
                    }
                    else {

                        for(ResponseResale resale: responseList)
                        {
                            ArrayList<String> imageList = new ArrayList<>();
                            for(int j =1;j<=resale.getTotalImage();j++)
                            {
                                imageList.add("https://transflyhome.club/resaleimage/" + resale.getId() + "/" + j);
                            }

                            resale.setImageList(imageList);
                        }

                        olxRecyclerAdapter.notifyDataSetChanged();

                        no_vehicle_found.setVisibility(View.GONE);

                    }
                } else {

                    Toast.makeText(fragmentActivity, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    no_internet_connection.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

                parent_of_loading.setVisibility(View.GONE);
                no_internet_connection.setVisibility(View.VISIBLE);

            }
        });
    }
}