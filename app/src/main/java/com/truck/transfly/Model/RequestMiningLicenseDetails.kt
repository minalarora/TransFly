package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestMiningLicenseDetails {
    @SerializedName("mininglicense")
    var miningLicense: String?  =null

    @SerializedName("mininglicenseimage")
    var miningLicenseImage: String?  =null
}