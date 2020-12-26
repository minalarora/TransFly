package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestFeedback {

    @SerializedName("rating")
    var rating: Int? = null

    @SerializedName("feedback")
    var feedback: String ? = null
}