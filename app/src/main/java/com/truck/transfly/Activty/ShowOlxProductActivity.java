package com.truck.transfly.Activty;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;

import com.truck.transfly.Adapter.ViewPagerImageCarouselAdapter;
import com.truck.transfly.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator;

public class ShowOlxProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_olx_product);

        initViewPager();
    }

    private void initViewPager() {

        List<String> stringList=new ArrayList<>();

        for (int i = 0; i < 10; i++) {

            stringList.add("s");

        }

        ViewPager viewPager=findViewById(R.id.viewPager);
        ViewPagerImageCarouselAdapter viewPagerImageCarouselAdapter=new ViewPagerImageCarouselAdapter(ShowOlxProductActivity.this,stringList);
        viewPager.setAdapter(viewPagerImageCarouselAdapter);
        ScrollingPagerIndicator scrollingPagerIndicator=findViewById(R.id.indicatorCondition);
        scrollingPagerIndicator.attachToPager(viewPager);
        scrollingPagerIndicator.setDotCount(0);
        scrollingPagerIndicator.setDotColor(Color.parseColor("#ffffff"));
        scrollingPagerIndicator.setSelectedDotColor(ContextCompat.getColor(Objects.requireNonNull(ShowOlxProductActivity.this),R.color.quantum_pink));
        scrollingPagerIndicator.setVisibleDotCount(7);

        // load images in viewpager together
        viewPager.setOffscreenPageLimit(6);


    }


}