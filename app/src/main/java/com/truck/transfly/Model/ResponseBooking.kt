package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class ResponseBooking {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("mine")
    var minename: String? = null

    @SerializedName("loading")
    var loading: String? = null

    @SerializedName("status")
    var status: String? = null

    @SerializedName("etl")
    var etl : String? = null

    @SerializedName("mineid")
    var mineid: Int? = null

    @SerializedName("createdAt")
    var date: String? = null
}