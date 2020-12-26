package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestAadhaarDetails {
    @SerializedName("aadhaar")
    var aadhaar: String?  =null

    @SerializedName("aadhaarimage")
    var aadhaarimage: String?  =null
}