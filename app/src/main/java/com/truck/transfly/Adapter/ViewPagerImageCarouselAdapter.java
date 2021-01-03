package com.truck.transfly.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.truck.transfly.R;

import java.util.List;

public class ViewPagerImageCarouselAdapter extends PagerAdapter {
    final java.util.List<String> stringList;
    private final Context context;
    LayoutInflater layoutInflater;

    public ViewPagerImageCarouselAdapter(Context context, List<String> stringList) {

        stringList.clear();
        stringList.add("https://static2.srcdn.com/wordpress/wp-content/uploads/2020/01/Girls-HBO-Lena-Dunham.jpg");
        stringList.add("https://images.pexels.com/photos/1468379/pexels-photo-1468379.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        stringList.add("https://images.pexels.com/photos/2646841/pexels-photo-2646841.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        stringList.add("https://images.pexels.com/photos/458768/pexels-photo-458768.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        stringList.add("https://images.pexels.com/photos/1386604/pexels-photo-1386604.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500");
        stringList.add("https://hips.hearstapps.com/hmg-prod.s3.amazonaws.com/images/hbz-met-gala-best-dressed-2010-gettyimages-102575534-1525207494.jpg");

        this.context = context;
        this.stringList = stringList;

    }

    @Override
    public int getCount() {
        return stringList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view,@NonNull Object object) {
        return view == object;
    }

    @SuppressLint("ClickableViewAccessibility")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        RelativeLayout view = (RelativeLayout) layoutInflater.inflate(R.layout.custom_image_layout_curosel, null);
        final ImageView imageView = view.findViewById(R.id.imageOfProduct);

        Glide.with(context).asBitmap().load(stringList.get(position)).diskCacheStrategy(DiskCacheStrategy.ALL).into(imageView);
        // for an ClothStore

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position,@NonNull Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }

}
