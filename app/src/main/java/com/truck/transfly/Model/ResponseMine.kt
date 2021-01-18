package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class ResponseMine {
    @SerializedName("id")
    var id: Int? = 0

    @SerializedName("name")
    var name: String? = null

    @SerializedName("area")
    var area: String? = null

    @SerializedName("trailer")
    var trailer: Boolean? = null

    @SerializedName("active")
    var active: Boolean? = null

    @SerializedName("tyres")
    var tyres: Int? = 0

    @SerializedName("bodytype")
    var bodytype: String? = null

    @SerializedName("loading")
    var loading: ArrayList<ResponseLoading>? = ArrayList<ResponseLoading>();

//    @SerializedName("rate")
//    var rate: Int? = null
//
//    @SerializedName("etl")
//    var etl: Int? = null


    @SerializedName("latitude")
    var latitude: String? = null

    @SerializedName("longitude")
    var longitude: String? = null

    @SerializedName("arealatitude")
    var arealatitude: String? = null

    @SerializedName("arealongitude")
    var arealongitude: String? = null

    @SerializedName("landmark")
    var landmark: String? = null

    @SerializedName("fieldstaff")
    var fieldstaff: String? = null

    @SerializedName("areamanager")
    var areamanager: String? = null

    @SerializedName("areaimageurl")
    var areaimageurl: String? = null
    
    constructor(id: Int?, name: String?, area: String?, trailer: Boolean?, tyres: Int?, bodytype: String?, loading: ArrayList<ResponseLoading>?, latitude: String?, longitude: String?, arealatitude: String?, arealongitude: String?, landmark: String?, fieldstaff: String?, areamanager: String?) {
        this.id = id
        this.name = name
        this.area = area
        this.trailer = trailer
        this.tyres = tyres
        this.bodytype = bodytype
        this.loading = loading
        this.latitude = latitude
        this.longitude = longitude
        this.arealatitude = arealatitude
        this.arealongitude = arealongitude
        this.landmark = landmark
        this.fieldstaff = fieldstaff
        this.areamanager = areamanager
    }

    override fun toString(): String {
        return "ResponseMine(id=$id, name=$name, area=$area, trailer=$trailer, tyres=$tyres, bodytype=$bodytype, loading=$loading,  latitude=$latitude, longitude=$longitude, arealatitude=$arealatitude, arealongitude=$arealongitude, landmark=$landmark, fieldstaff=$fieldstaff, areamanager=$areamanager)"
    }


    /* id:
    {
     type: Number,
     default: () => {
        return parseInt(nanoid())
     }
    },
     name:{
         type: String,
         unique: true,
         required: true
     },
     area:{
         type: String,
         required: true
     },
     trailer:
     {
         type: Boolean,
         default: true,
     },
     active:
     {
         type: Boolean,
         default: true,
     },
     tyres:
     {
         type: Number,
         required: true
     },
     bodytype:
     {
         type: String,
         enum : ['ATTACHED','NON-ATTACHED','BOTH'],
         default: 'ATTACHED'
     },
     loading:
     {
         type: [String],
         required: true,
         validate: [arrayLimit, 'Atleast one loading is required']
     },
     rate:
     {
         type: Number,
         required: true
     },
     etl:
     {
         type: Number,
         required: true,
         validate(value)
         {
            if(value > 24)
            {
                throw new Error("Invalid Estimate Time of Loading")
            }
         }
     },
     latitude:
     {
         type: String,
         required: true
     },
     longitude:
     {
         type: String,
         required: true
     },
     landmark:{
         type: String,
         default: "NOT AVAILABLE"
     },
     transporter:
    {
        type: String,
        default: undefined,
        ref: 'Transporter'
    },
    areamanager:
    {
        type: String,
        default: undefined,
        ref: 'AreaManager'
    },
    fieldstaff:
    {
        type:String,
        default: undefined,
        ref: 'Fieldstaff'
    }
    * */


}