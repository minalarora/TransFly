package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class ResponseRefer {

    @SerializedName("id")
    var id: Int? = 0

    @SerializedName("text")
    var text: String? = null

    @SerializedName("image")
    var image: String? = null

    @SerializedName("status")
    var status: Int? = 0

    @SerializedName("createdAt")
    var date: String? = null

   }