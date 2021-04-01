package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestBooking {



    var vehicle: String? = null


    var mineid: Int?=0


    var minename: String? = null


    var loading: String? = null


    var contact: String? = null
    override fun toString(): String {
        return "RequestBooking(vehicle=$vehicle, mineid=$mineid, minename=$minename, loading=$loading, contact=$contact)"
    }


}