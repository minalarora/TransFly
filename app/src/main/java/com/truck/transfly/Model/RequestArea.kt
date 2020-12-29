package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestArea {
    var name: String? = null

    @SerializedName("arealatitude")
    var arealatitude: String? = null

    @SerializedName("arealongitude")
    var arealongitude: String? = null

    constructor(name: String?, arealatitude: String?, arealongitude: String?) {
        this.name = name
        this.arealatitude = arealatitude
        this.arealongitude = arealongitude
    }


    override fun toString(): String {
        return "RequestArea(arealatitude=$arealatitude, arealongitude=$arealongitude)"
    }


}