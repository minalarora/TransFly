package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestRating {

    @SerializedName("rating")
    var rating: Float? = 0f;

    @SerializedName("message")
    var message: String? = null;
}