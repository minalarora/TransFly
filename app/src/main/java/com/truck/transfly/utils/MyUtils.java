package com.truck.transfly.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;

import com.truck.transfly.Model.WhoModel;
import com.truck.transfly.R;
import com.wooplr.spotlight.SpotlightView;
import com.wooplr.spotlight.utils.SpotlightListener;

public class MyUtils {

    private listener listener;

    public interface listener{

        void clickedSpotlight(String perfect);

    }

    public void setListener(listener listener){

        this.listener =listener;

    }
    public static WhoModel returnModel(int drawableId, String OwnerName, String belowOwner, String belowOwner2, String keyword){

        WhoModel whoModel=new WhoModel();
        whoModel.setImage_url(drawableId);
        whoModel.setOwnerType(OwnerName);
        whoModel.setBelowOwner(belowOwner);
        whoModel.setKeyword(keyword);
        whoModel.setBelow2Owner(belowOwner2);

        return whoModel;
    }

    public void spotLightOnProfile(final View view, final String id, final Context context, final String message, final String title) {

        new Handler(context.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

                new SpotlightView.Builder(MyUtils.getActivity(context))
                        .introAnimationDuration(400)
                        .enableRevealAnimation(true)
                        .performClick(true)
                        .fadeinTextDuration(400)
                        .headingTvColor(Color.parseColor("#eb273f"))
                        .headingTvSize(24)
                        .headingTvText(title)
                        .subHeadingTvColor(Color.parseColor("#ffffff"))
                        .subHeadingTvSize(16)
                        .subHeadingTvText(message)
                        .maskColor(Color.parseColor("#dc000000"))
                        .target(view)
                        .lineAnimDuration(400)
                        .lineAndArcColor(Color.parseColor("#eb273f"))
                        .dismissOnTouch(true)
                        .dismissOnBackPress(true)
                        .enableDismissAfterShown(true)
                        .usageId(id) //UNIQUE ID
                        .setTypeface(ResourcesCompat.getFont(context, R.font.font_opensans))
                        .setListener(new SpotlightListener() {
                            @Override
                            public void onUserClicked(String s) {

                                if(listener!=null)
                                    listener.clickedSpotlight(s);
                            }
                        })
                        .show();

            }
        });


    }

    static Activity getActivity(Context context)
    {
        if (context == null)
        {
            return null;
        }
        else if (context instanceof ContextWrapper)
        {
            if (context instanceof Activity)
            {
                return (Activity) context;
            }
            else
            {
                return getActivity(((ContextWrapper) context).getBaseContext());
            }
        }

        return null;
    }

}


