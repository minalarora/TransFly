package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class ResponseLoading {
    @SerializedName("loadingname")
    var loadingName: String? = null

    @SerializedName("rate")
    var rate:Int? = 0

    @SerializedName("etl")
    var etl:Int? = 0

    @SerializedName("active")
    var active: Boolean? = true


    var loadingimage: String? = "https://transflyhome.club/loading/" + loadingName
}