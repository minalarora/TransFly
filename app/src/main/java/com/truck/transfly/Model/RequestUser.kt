package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestUser {
    @SerializedName("name")
    var name: String? = null

    @SerializedName("email")
    var email: String? = null


    @SerializedName("mobile")
    var mobile: String? = null

    @SerializedName("password")
    var password: String?  =null

    @SerializedName("firebase")
    var firebase : String? = null

    constructor(name: String?, email: String?, mobile: String?, password: String?,firebase : String?) {
        this.name = name
        this.email = email
        this.mobile = mobile
        this.password = password
        this.firebase = firebase
    }
}