package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestRating {

    @SerializedName("rating")
    var rating: Int? = 0;

    @SerializedName("message")
    var message: String? = null;
}