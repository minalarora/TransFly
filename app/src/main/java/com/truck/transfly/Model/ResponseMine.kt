package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class ResponseMine {
    @SerializedName("id")
    var id: Int? = 0

    @SerializedName("name")
    var name: String? = null

    @SerializedName("area")
    var area: String? = null

    @SerializedName("trailer")
    var trailer: Boolean? = null

    @SerializedName("active")
    var active: Boolean? = null

    @SerializedName("tyres")
    var tyres: Int? = 0

    @SerializedName("bodytype")
    var bodytype: String? = null

    @SerializedName("loading")
    var loading: ArrayList<String>? = null

    @SerializedName("rate")
    var rate: Int? = null

    @SerializedName("etl")
    var etl: Int? = null

    @SerializedName("latitude")
    var latitude: String? = null

    @SerializedName("longitude")
    var longitude: String? = null

    @SerializedName("landmark")
    var landmark: String? = null


}