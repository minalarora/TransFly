package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class UpdateVehicle {
    @SerializedName("id")
    var id: Int? = 0

    @SerializedName("contact")
    var contact: String? = null
}