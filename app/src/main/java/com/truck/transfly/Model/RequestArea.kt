package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class RequestArea {
    var name: String? = null


    var arealatitude: String? = null


    var arealongitude: String? = null

    var areaimage: String? = null

    constructor(name: String?, arealatitude: String?, arealongitude: String?,areaimage: String?) {
        this.name = name
        this.arealatitude = arealatitude
        this.arealongitude = arealongitude
        this.areaimage = areaimage
    }


    override fun toString(): String {
        return "RequestArea(arealatitude=$arealatitude, arealongitude=$arealongitude)"
    }


}