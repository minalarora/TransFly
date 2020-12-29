package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class ResponseBooking {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("loading")
    var loading: String? = null

    @SerializedName("status")
    var status: String? = null

    @SerializedName("mineid")
    var mineid: Int? = null

    @SerializedName("minename")
    var minename: String? = null

    @SerializedName("createdAt")
    var date: String? = null

    @SerializedName("vehicle")
    var vehiclename: String? = null

    @SerializedName("vehicleowner")
    var vehicleowner: String? = null

    @SerializedName("vehicleownermobile")
    var vehicleownermobile: String? = null
}