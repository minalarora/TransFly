package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestInvoice {
    @SerializedName("id")
    var id: Int? = 0

    @SerializedName("mineid")
    var mineid: Int? = null

    @SerializedName("owner")
    var owner: String? = null

    @SerializedName("minename")
    var minename: String? = null

    @SerializedName("loading")
    var loading: String? = null

    @SerializedName("vehicle")
    var vehiclenumber: String? = null

    @SerializedName("vehicleowner")
    var vehicleowner: String? = null

    @SerializedName("vehicleownermobile")
    var vehicleownermobile: String? = null

    @SerializedName("tonnage")
    var tonnage: Double? = 0.0

    @SerializedName("rate")
    var rate: Double? = 0.0

    @SerializedName("hsd")
    var hsd: Double? = 0.0

    @SerializedName("cash")
    var cash: Double? = 0.0

    @SerializedName("transporter")
    var transporterMobile: String?  =null


}