package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestVehicle {


    @SerializedName("number")
    var number: String? = null

    @SerializedName("rc")
    var rc: String? = null

    @SerializedName("rcimage")
    var rcimage: String? = null

    @SerializedName("vehiclename")
    var vehiclename: String? = null

}