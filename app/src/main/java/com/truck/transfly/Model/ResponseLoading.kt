package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class ResponseLoading {
    @SerializedName("loadingname")
    var loadingName: String? = null

    @SerializedName("rate")
    var rate:Int? = 0

    @SerializedName("etl")
    var etl:Int? = 0


    var loadingimage: String? = "https://transfly-ftr2t.ondigitalocean.app/loading/" + loadingName
}