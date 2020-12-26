package com.truck.transfly.utils;

import com.truck.transfly.Model.WhoModel;

public class MyUtils {

    public static WhoModel returnModel(int drawableId,String OwnerName,String belowOwner,String belowOwner2){

        WhoModel whoModel=new WhoModel();
        whoModel.setImage_url(drawableId);
        whoModel.setOwnerType(OwnerName);
        whoModel.setBelowOwner(belowOwner);
        whoModel.setBelow2Owner(belowOwner2);

        return whoModel;
    }

}


