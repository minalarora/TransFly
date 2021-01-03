package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestCoordinates {

    @SerializedName("latitude")
    var latitude: Double? = 0.0

    @SerializedName("longitude")
    var longitude: Double? = 0.0
}