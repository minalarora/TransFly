package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestArea {

    @SerializedName("arealatitude")
    var arealatitude: String? = null

    @SerializedName("arealongitude")
    var arealongitude: String? = null

    constructor( arealatitude: String?, arealongitude: String?) {

        this.arealatitude = arealatitude
        this.arealongitude = arealongitude
    }

    override fun toString(): String {
        return "RequestArea(arealatitude=$arealatitude, arealongitude=$arealongitude)"
    }


}