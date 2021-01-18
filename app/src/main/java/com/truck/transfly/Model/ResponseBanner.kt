package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class ResponseBanner {
    @SerializedName("id")
    var id: Int? = 0

    var imageUrl: String? = null

    @SerializedName("bannertype")
    var bannerType: String? = "none";

    //booking
    //resale
    //none
}