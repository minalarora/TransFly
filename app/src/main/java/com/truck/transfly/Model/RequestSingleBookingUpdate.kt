package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestSingleBookingUpdate
{
    @SerializedName("vehicle")
    var vehicle: String? = null

    @SerializedName("status")
    var status: String? = "COMPLETED"
}