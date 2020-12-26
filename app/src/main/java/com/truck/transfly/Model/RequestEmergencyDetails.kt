package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestEmergencyDetails {

    @SerializedName("ename")
    var emergencyName: String? = null

    @SerializedName("emobile")
    var emergencyNumber: String? = null

    @SerializedName("erelation")
    var emergencyRelation: String? = null
}