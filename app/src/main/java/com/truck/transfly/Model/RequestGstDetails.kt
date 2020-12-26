package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestGstDetails {
    @SerializedName("gst")
    var gst: String?  =null

    @SerializedName("gstimage")
    var gstimage: String?  =null
}