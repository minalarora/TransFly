package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.truck.transfly.Adapter.SearchAdapter;
import com.truck.transfly.Model.RequestArea;
import com.truck.transfly.Model.ResponseLoading;
import com.truck.transfly.Model.ResponseMine;
import com.truck.transfly.R;
import com.truck.transfly.utils.ApiClient;
import com.truck.transfly.utils.ApiEndpoints;
import com.truck.transfly.utils.PreferenceUtil;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UpdateRateActivity extends AppCompatActivity {

    private List<String> stringList = new ArrayList<>();
    private ArrayList<ResponseMine> mines = new ArrayList<>();
    private long last_text_edit;
    private ArrayList<ResponseMine> responseMineArrayList=new ArrayList<>();
    private Retrofit retrofit = null;
    private ApiEndpoints api = null;
    private long delay = 200; // 1 seconds after user stops typing
    private Handler handler;
    private ArrayList<String> loadinglist = new ArrayList<>();
    private ArrayList<RequestArea> arealist = new ArrayList<>();
    private FrameLayout parent_of_loading;
    private RelativeLayout no_internet_connection;
    private ArrayAdapter<String> adapter;
    private EditText fromSearch;
    private EditText to_search;
    private RelativeLayout to_search_parent;
    private boolean isStartWrite;
    private RecyclerView recyclerView;
    private String selectedSweet;
    private SearchAdapter searchAdapter;
    private ResponseMine responseMineGlobal;
    private boolean vehicleOwner = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_rate);
        retrofit = ApiClient.getRetrofitClient();
        if (retrofit != null) {
            api = retrofit.create(ApiEndpoints.class);
        }

        TextView bookingButton =findViewById(R.id.bookingButton);

        Intent intent = getIntent();

        if(intent!=null){

            vehicleOwner = intent.getBooleanExtra("vehicle",false);

//            if(!vehicleOwner)
//                //bookingButton.setText("Check Rate And ETL");


        }

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });

        fromSearch = findViewById(R.id.from_search);
        to_search = findViewById(R.id.to_search);

        parent_of_loading = findViewById(R.id.parent_of_loading);
        parent_of_loading.setVisibility(View.GONE);

        no_internet_connection = findViewById(R.id.no_internet_connection);
        findViewById(R.id.pullToRefresh_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loadinglist.clear();
                mines.clear();
                searchAdapter.notifyDataSetChanged();
                no_internet_connection.setVisibility(View.GONE);
                getMinesFromServer(PreferenceUtil.getData(UpdateRateActivity.this, "token"));

            }
        });

        to_search_parent = findViewById(R.id.to_search_parent);
//        to_search_parent.setVisibility(View.GONE);

        findViewById(R.id.creaate_booking).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fromSearch.setError(null);
                to_search.setError(null);

                if(TextUtils.isEmpty(fromSearch.getText().toString())){

                    fromSearch.setError("From Mines is Empty*");
                    fromSearch.requestFocus();

                }else if(!isResponseMines(fromSearch.getText().toString())){

                    fromSearch.setError(fromSearch.getText().toString()+" is Not Valid Mines*");
                    fromSearch.requestFocus();

                }  else if(TextUtils.isEmpty(to_search.getText().toString())){

                    to_search.setError("Loading is Empty*");
                    to_search.requestFocus();

                } else if(!isLoadingAvailable(to_search.getText().toString())){

                    to_search.setError(to_search.getText().toString()+" is Not Valid Loading*");
                    to_search.requestFocus();

                }  else {

                    if(responseMineGlobal==null){

                        Toast.makeText(UpdateRateActivity.this, "Select a Mines", Toast.LENGTH_SHORT).show();

                        fromSearch.requestFocus();

                    } else {


                        ArrayList<ResponseLoading> list = (ArrayList<ResponseLoading>) responseMineGlobal.getLoading();
//                        for(ResponseLoading loading: list)
//                        {
//                            if(loading.getLoadingname().equalsIgnoreCase(to_search.getText().toString()))
//                            {
//                                if(loading.getActive() == false)
//                                {
//                                    Toast.makeText(UpdateRateActivity.this, "This Mine is INACTIVE for the day, please try another Mine for your Loading.", Toast.LENGTH_SHORT).show();
//
//                                    return;
//                                }
//                            }
//                        }
                        Intent intent = new Intent(UpdateRateActivity.this, UpdateRate2Activity.class);
                        intent.putExtra("vehicle",vehicleOwner);
                        ArrayList<ResponseLoading> arrayList =  responseMineGlobal.getLoading();
                        int rate = 5;
                        int etl = 0;
                        for(ResponseLoading l: arrayList)
                        {
                            if(l.getLoadingname().equalsIgnoreCase(to_search.getText().toString()))
                            {
                                rate = l.getRate();
                                etl = l.getEtl();

                            }
                        }

                        //loading =>
                        intent.putExtra("mineid", responseMineGlobal.getId());
                        intent.putExtra("minename", fromSearch.getText().toString());
                        intent.putExtra("loading", to_search.getText().toString());
                        intent.putExtra("tyres",responseMineGlobal.getTyres());
                        intent.putExtra("trailor",responseMineGlobal.getTrailer());
                        intent.putExtra("rate", rate);
                        intent.putExtra("etl", etl);

                        startActivity(intent);

                    }

                }

            }
        });

        handler = new Handler(getMainLooper());

        ListView listViewLoading = findViewById(R.id.list_item_loading);
        adapter = new ArrayAdapter<String>(UpdateRateActivity.this,
                android.R.layout.simple_list_item_1, android.R.id.text1, loadinglist);

        listViewLoading.setAdapter(adapter);

        listViewLoading.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                selectedSweet = listViewLoading.getItemAtPosition(position).toString();
                to_search.setText(selectedSweet);
                to_search.setSelection(selectedSweet.length());
                to_search_parent.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                to_search.requestFocus();
                to_search.setError(null);

            }
        });

        fromSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {

                    listViewLoading.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
//                    to_search_parent.setVisibility(View.GONE);
                    searchAdapter.notifyDataSetChanged();

                }

            }
        });

        to_search.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                listViewLoading.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                adapter.notifyDataSetChanged();

            }
        });

//        fromSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                adapter.getFilter().filter(s);
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        fromSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                ArrayList<ResponseMine> minesByMines = getMine(s.toString());
                responseMineArrayList.clear();
                responseMineArrayList.addAll(minesByMines);
                searchAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

                if(s.length()==0){

                    responseMineArrayList.clear();
                    responseMineArrayList.addAll(mines);
                    searchAdapter.notifyDataSetChanged();

                }

            }
        });

        responseMineArrayList.addAll(mines);

        recyclerView = findViewById(R.id.recyclerView);
        searchAdapter = new SearchAdapter(UpdateRateActivity.this, responseMineArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(UpdateRateActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(searchAdapter);

        searchAdapter.setOnClickListener(new SearchAdapter.onClickListener() {

            @Override
            public void onClick(ResponseMine responseMine) {

                fromSearch.setText(responseMine.getName());
                fromSearch.setSelection(fromSearch.length());
                responseMineGlobal=responseMine;
                to_search_parent.setVisibility(View.VISIBLE);
                to_search.setError(null);
                fromSearch.setError(null);

                recyclerView.setVisibility(View.GONE);
                listViewLoading.setVisibility(View.VISIBLE);

                ArrayList<String> loading = getLoading(responseMine);

                to_search.requestFocus();

                loadinglist.clear();
                loadinglist.addAll(loading);
                adapter.notifyDataSetChanged();


            }
        });

        getMinesFromServer(PreferenceUtil.getData(UpdateRateActivity.this, "token"));

    }

    private ArrayList<String> getLoading(ResponseMine mine)
    {
        ArrayList<String> loadingName = new ArrayList<>();
        for (ResponseLoading responseLoading : mine.getLoading()) {

            loadingName.add(responseLoading.getLoadingname());

        }
        return loadingName;
    }

    private ArrayList<ResponseMine> getMine(String minename)
    {
        ArrayList<ResponseMine> selectedlist = new ArrayList<>();
        for(ResponseMine mine: mines)
        {
            if (mine.getName().contains(minename.toUpperCase()))
            {
                selectedlist.add(mine);
            }
        }
        return selectedlist;
    }

    private boolean isResponseMines(String s) {

        for (ResponseMine mine : mines) {

            if(mine.getName().equals(s))
                return true;

        }

        return false;

    }

    private boolean isLoadingAvailable(String selectedSweet) {

        ArrayList<String> loading = getLoading(responseMineGlobal);

        for (String s : loading) {

            if(s.equals(selectedSweet))
                return true;

        }

        return false;

    }

    private Runnable toSearchRunner = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                // TODO: do what you need here
                // ............
                // ............
                DoStuffToSearch();
            }
        }
    };

    private void DoStuffToSearch() {

        Toast.makeText(this, "yes To Search", Toast.LENGTH_SHORT).show();

    }

    private Runnable fromSearchRunner = new Runnable() {
        public void run() {
            if (System.currentTimeMillis() > (last_text_edit + delay - 500)) {
                // TODO: do what you need here
                // ............
                // ............
                DoStuffFromSearch();
            }
        }
    };

    private void DoStuffFromSearch() {

        Toast.makeText(this, "yes FROM Search", Toast.LENGTH_SHORT).show();

    }

    //all mines

    private void getMinesFromServer(String token) {

        parent_of_loading.setVisibility(View.VISIBLE);
        no_internet_connection.setVisibility(View.GONE);

        api.getAllMineVehicleOwner(token).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                parent_of_loading.setVisibility(View.GONE);

                if (response.code() == 200) {

                    Type collectionType = new TypeToken<ArrayList<ResponseMine>>() {
                    }.getType();
                    try {
                        mines.addAll(new Gson().fromJson(response.body().string().toString(), collectionType));
                        responseMineArrayList.addAll(mines);
                        searchAdapter.notifyDataSetChanged();
                    } catch (IOException e) {
                    }
                    if (mines.isEmpty()) {
                        Log.d("minal", "mine not found");
                    } else {
                        Log.d("minal", mines.toString());
                        //for area name
                        HashMap<String, RequestArea> areas = new HashMap<>();
                        //for destination
                        Set<String> loadings = new HashSet<>();
                        Set<RequestArea> areass = new HashSet<>();
                        for (ResponseMine mine : mines) {
                            areas.put(mine.getArea(), new RequestArea(mine.getArea(), mine.getArealatitude(), mine.getArealongitude(),mine.getAreaimageurl()));
                            for (ResponseLoading loading : mine.getLoading()) {
                                loadings.add(loading.getLoadingname());
                            }
                        }

                        for (Map.Entry<String, RequestArea> a : areas.entrySet()) {
                            areass.add(a.getValue());
                        }

                        loadinglist.addAll(loadings);
                        arealist.addAll(areass);
                        adapter.notifyDataSetChanged();

                    }

                } else {

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

    private ArrayList<ResponseMine> getMinesByMines(String mine, String loading) {

        ArrayList<ResponseMine> selectedmines = new ArrayList<>();

        for (ResponseMine m : mines) {
            if (m.getName().toLowerCase().contains(mine.toLowerCase()) && m.getLoading().contains(loading)) {
                selectedmines.add(m);
            }
        }
        return selectedmines;
    }


}