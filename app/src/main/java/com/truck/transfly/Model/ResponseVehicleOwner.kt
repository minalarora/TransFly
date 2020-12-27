package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class ResponseVehicleOwner {
    @SerializedName("name")
    var  name: String? = null;

    @SerializedName("mobile")
    var mobile: String? = null;

    @SerializedName("email")
    var email: String? = null

    @SerializedName("password")
    var password: String? = null

    @SerializedName("status")
    var status: Int? = null

    @SerializedName("accountno")
    var accountno: String? = null

    @SerializedName("ifsc")
    var ifsc: String? = null

    @SerializedName("bankname")
    var bankname: String? = null

    @SerializedName("pan")
    var pan: String? = null

    @SerializedName("tds")
    var tds: String? = null

    @SerializedName("emergencycontact")
    var emergencycontact: String? = null

    @SerializedName("token")
    var token: String? = null

}