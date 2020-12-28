package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestTicket {
    /*
    *  category:
    {
        type: String,
        required: true
    },
    message:
    {
        type: String
    }
    * */
    @SerializedName("category")
    var category: String? = null;

    @SerializedName("message")
    var message: String? = null;
}