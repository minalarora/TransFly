package com.truck.transfly.Model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class ResponseLoading() : Parcelable {
    //@SerializedName("loadingname")
    var loadingname: String? = null

    //@SerializedName("rate")
    var rate:Int? = 0

    //@SerializedName("etl")
    var etl:Int? = 0

    //@SerializedName("active")
    var active: Boolean? = true


    var loadingimage: String? = "https://transflyhome.club/loading/" + loadingname

    constructor(parcel: Parcel) : this() {
        loadingname = parcel.readString()
        rate = parcel.readValue(Int::class.java.classLoader) as? Int
        etl = parcel.readValue(Int::class.java.classLoader) as? Int
        active = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        loadingimage = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(loadingname)
        parcel.writeValue(rate)
        parcel.writeValue(etl)
        parcel.writeValue(active)
        parcel.writeString(loadingimage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResponseLoading> {
        override fun createFromParcel(parcel: Parcel): ResponseLoading {
            return ResponseLoading(parcel)
        }

        override fun newArray(size: Int): Array<ResponseLoading?> {
            return arrayOfNulls(size)
        }
    }
}