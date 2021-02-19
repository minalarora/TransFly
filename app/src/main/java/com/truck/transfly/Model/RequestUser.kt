package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestUser {

    var name: String? = null


    var email: String? = null



    var mobile: String? = null


    var password: String?  =null


    var firebase : String? = null

    constructor(name: String?, email: String?, mobile: String?, password: String?,firebase : String?) {
        this.name = name
        this.email = email
        this.mobile = mobile
        this.password = password
        this.firebase = firebase
    }
}