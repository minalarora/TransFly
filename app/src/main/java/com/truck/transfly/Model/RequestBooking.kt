package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestBooking {


    @SerializedName("vehicle")
    var vehiclenumber: String? = null

    @SerializedName("mineid")
    var mineid: Int?=0

    @SerializedName("minename")
    var minename: String? = null

    @SerializedName("loading")
    var loading: String? = null

    @SerializedName("contact")
    var contact: String? = null
}