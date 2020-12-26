package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestBankDetails {
    @SerializedName("bankname")
    var bankname: String?  =null

    @SerializedName("accountno")
    var accountno: String?  = null

    @SerializedName("ifsc")
    var ifsc: String?  = null

    @SerializedName("bankimage")
    var bankimage: String?  =null
}