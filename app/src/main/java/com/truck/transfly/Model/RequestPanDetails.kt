package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestPanDetails {
    @SerializedName("pan")
    var pan: String?  =null

    @SerializedName("panimage")
    var panimage: String?  =null
}