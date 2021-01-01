package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.truck.transfly.Adapter.SearchAdapter;
import com.truck.transfly.Model.ResponseMine;
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SearchBarActivity extends AppCompatActivity {

    private List<String> stringList=new ArrayList<>();
    private long last_text_edit;
    private long delay = 1000; // 1 seconds after user stops typing
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_bar);

        EditText fromSearch =findViewById(R.id.from_search);
        EditText to_search=findViewById(R.id.to_search);

        handler = new Handler(getMainLooper());

        fromSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                handler.removeCallbacks(fromSearchRunner);

            }

            @Override
            public void afterTextChanged(Editable s) {

                //avoid triggering event when text is empty
                if (s.length() > 0) {

                    last_text_edit = System.currentTimeMillis();
                    handler.postDelayed(fromSearchRunner, delay);

                } else {

//                    searchModelList.clear();
//
//                    getAllDataFromServer();
//
//                    searchAdapter.notifyDataSetChanged();

                }
            }
        });

        to_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                handler.removeCallbacks(toSearchRunner);

            }

            @Override
            public void afterTextChanged(Editable s) {

                //avoid triggering event when text is empty
                if (s.length() > 0) {

                    last_text_edit = System.currentTimeMillis();
                    handler.postDelayed(toSearchRunner, delay);

                } else {

//                    searchModelList.clear();
//
//                    getAllDataFromServer();
//
//                    searchAdapter.notifyDataSetChanged();

                }

            }
        });

        for (int i = 0; i < 10; i++) {

            stringList.add("s");

        }

        RecyclerView recyclerView =findViewById(R.id.recyclerView);
        SearchAdapter searchAdapter=new SearchAdapter(SearchBarActivity.this,stringList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(SearchBarActivity.this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(searchAdapter);

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
    ArrayList<ResponseMine> mines  =  new ArrayList<>();

    private ArrayList<String> getLoading()
    {
        HashSet<String> set = new HashSet<>();
        for(ResponseMine m : mines)
        {
            for(String loading: m.getLoading())
            {
                set.add(loading);
            }
        }



        return new ArrayList<>(set);
//        ArrayList<ResponseMine> selectedmines = new ArrayList<>();
//        for(ResponseMine m: mines)
//        {
//            for(String l: m.getLoading())
//            {
//                if(l.equalsIgnoreCase(loading))
//                {
//                    selectedmines.add(m);
//                }
//            }
//        }
//        return selectedmines;
    }

    private ArrayList<ResponseMine> getMinesByMines(String mine,String loading)
    {
        ArrayList<ResponseMine> selectedmines = new ArrayList<>();
        for(ResponseMine m: mines)
        {
            if(m.getName().contains(mine) && m.getLoading().contains(loading))
            {
                    selectedmines.add(m);
            }
        }
        return selectedmines;
    }

}