package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestCredentials {

    @SerializedName("mobile")
    var mobile: String? = null

    @SerializedName("password")
    var password: String? = null

}