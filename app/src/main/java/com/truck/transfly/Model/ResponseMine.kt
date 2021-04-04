package com.truck.transfly.Model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class ResponseMine() : Parcelable {
   // @SerializedName("id")
    var id: Int? = 0

    //@SerializedName("name")
    var name: String? = null

    //@SerializedName("area")
    var area: String? = null

    //@SerializedName("trailer")
    var trailer: Boolean? = null

    //@SerializedName("active")
    var active: Boolean? = null

    //@SerializedName("tyres")
    var tyres: Int? = 0

    //@SerializedName("bodytype")
    var bodytype: String? = null

    //@SerializedName("loading")
    var loading: ArrayList<ResponseLoading> = ArrayList<ResponseLoading>();

//    @SerializedName("rate")
//    var rate: Int? = null
//
//    @SerializedName("etl")
//    var etl: Int? = null


    //@SerializedName("latitude")
    var latitude: String? = null

    //@SerializedName("longitude")
    var longitude: String? = null

    //@SerializedName("arealatitude")
    var arealatitude: String? = null

    //@SerializedName("arealongitude")
    var arealongitude: String? = null

    //@SerializedName("landmark")
    var landmark: String? = null

    //@SerializedName("fieldstaff")
    var fieldstaff: String? = null

    //@SerializedName("areamanager")
    var areamanager: String? = null

    //@SerializedName("areaimageurl")
    var areaimageurl: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        name = parcel.readString()
        area = parcel.readString()
        trailer = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        active = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        tyres = parcel.readValue(Int::class.java.classLoader) as? Int
        bodytype = parcel.readString()
        latitude = parcel.readString()
        longitude = parcel.readString()
        arealatitude = parcel.readString()
        arealongitude = parcel.readString()
        landmark = parcel.readString()
        fieldstaff = parcel.readString()
        areamanager = parcel.readString()
        areaimageurl = parcel.readString()
       // parcel.readTypedList(loading.toMutableList(),ResponseLoading.CREATOR)
        loading = parcel.createTypedArrayList(ResponseLoading.CREATOR)!!
    }

    constructor(id: Int?, name: String?, area: String?, trailer: Boolean?, tyres: Int?, bodytype: String?, loading: ArrayList<ResponseLoading>?, latitude: String?, longitude: String?, arealatitude: String?, arealongitude: String?, landmark: String?, fieldstaff: String?, areamanager: String?) : this() {
        this.id = id
        this.name = name
        this.area = area
        this.trailer = trailer
        this.tyres = tyres
        this.bodytype = bodytype
        this.loading = loading!!
        this.latitude = latitude
        this.longitude = longitude
        this.arealatitude = arealatitude
        this.arealongitude = arealongitude
        this.landmark = landmark
        this.fieldstaff = fieldstaff
        this.areamanager = areamanager
    }

    override fun toString(): String {
        return "ResponseMine(name=$name, area=$area,)"
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
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(area)
        parcel.writeValue(trailer)
        parcel.writeValue(active)
        parcel.writeValue(tyres)
        parcel.writeString(bodytype)
        parcel.writeString(latitude)
        parcel.writeString(longitude)
        parcel.writeString(arealatitude)
        parcel.writeString(arealongitude)
        parcel.writeString(landmark)
        parcel.writeString(fieldstaff)
        parcel.writeString(areamanager)
        parcel.writeString(areaimageurl)
        parcel.writeTypedList(loading)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResponseMine> {
        override fun createFromParcel(parcel: Parcel): ResponseMine {
            return ResponseMine(parcel)
        }

        override fun newArray(size: Int): Array<ResponseMine?> {
            return arrayOfNulls(size)
        }
    }


}