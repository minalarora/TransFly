package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestEmergencyContact2 {
    @SerializedName("ename")
    var emergencyName: String? = null

    @SerializedName("erelation")
    var emergencyRelation: String? = null

    @SerializedName("emobile")
    var emergencyMobile: String? = null
}