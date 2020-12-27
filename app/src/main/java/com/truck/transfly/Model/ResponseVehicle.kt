package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class ResponseVehicle {
    @SerializedName("id")
    var id: Int? = 0

    @SerializedName("number")
    var number: String? = null

    @SerializedName("rc")
    var rc: String? = null

    @SerializedName("vehiclename")
    var vehiclename: String? = null

    @SerializedName("status")
    var status: Int? = 0


}