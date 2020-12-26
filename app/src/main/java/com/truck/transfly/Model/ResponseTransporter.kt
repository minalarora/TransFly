package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class ResponseTransporter {
    @SerializedName("name")
    var  name: String? = null;

    @SerializedName("mobile")
    var mobile: String? = null;

    @SerializedName("email")
    var email: String? = null

    @SerializedName("password")
    var password: String? = null

    @SerializedName("status")
    var status: Int? = null

    @SerializedName("gst")
    var gst: String? = null

    @SerializedName("sta")
    var sta: String? = null

    @SerializedName("pan")
    var aadhaar: String? = null

    @SerializedName("mininglicense")
    var mininglicense: String? = null


}