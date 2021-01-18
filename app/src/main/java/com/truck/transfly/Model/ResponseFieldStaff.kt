package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class ResponseFieldStaff {

    @SerializedName("id")
    var id: String? = null
    @SerializedName("name")
    var  name: String? = null;

    @SerializedName("mobile")
    var mobile: String? = null;

    @SerializedName("email")
    var email: String? = null

    @SerializedName("firebase")
    var firebase : String? = null

    @SerializedName("bankpersonname")
    var bankpersonname: String? = null

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

    @SerializedName("aadhaar")
    var aadhaar: String? = null

    @SerializedName("ename")
    var emergencyName: String? = null

    @SerializedName("erelation")
    var emergencyRelation: String? = null

    @SerializedName("emobile")
    var emergencyMobile: String? = null

    @SerializedName("token")
    var token: String? = null
    override fun toString(): String {
        return "ResponseFieldStaff(name=$name, mobile=$mobile, email=$email, status=$status, accountno=$accountno, ifsc=$ifsc, bankname=$bankname, pan=$pan, aadhaar=$aadhaar, token=$token)"
    }


}