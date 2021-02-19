package com.truck.transfly.Model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class ResponseResale() : Parcelable {
    //@SerializedName("1")
    var a: Boolean? = false;

    //@SerializedName("2")
    var b: Boolean? = false;

    //@SerializedName("3")
    var c: Boolean? = false;

    //@SerializedName("4")
    var d: Boolean? = false;

    //@SerializedName("5")
    var e: Boolean? = false;

    //@SerializedName("6")
    var f: Boolean? = false;

    //@SerializedName("7")
    var g: Boolean? = false;

    //@SerializedName("8")
    var h: Boolean? = false;

    //@SerializedName("9")
    var i: Boolean? = false;

    //@SerializedName("10")
    var j: Boolean? = false;

    //@SerializedName("11")
    var k: Boolean? = false;

    //@SerializedName("12")
    var l: Boolean? = false;

    //@SerializedName("id")
    var id: Int? = 0

    //@SerializedName("vehiclename")
    var vehiclename: String?= null;

    //@SerializedName("company")
    var company: String?= null;

    //@SerializedName("year")
    var year: String?= null;

   // @SerializedName("totalimage")
    var totalimage: Int?= 0;

    var imageList: ArrayList<String>? = ArrayList()

    constructor(parcel: Parcel) : this() {
        a = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        b = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        c = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        d = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        e = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        f = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        g = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        h = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        i = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        j = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        k = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        l = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        vehiclename = parcel.readString()
        company = parcel.readString()
        year = parcel.readString()
        totalimage = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(a)
        parcel.writeValue(b)
        parcel.writeValue(c)
        parcel.writeValue(d)
        parcel.writeValue(e)
        parcel.writeValue(f)
        parcel.writeValue(g)
        parcel.writeValue(h)
        parcel.writeValue(i)
        parcel.writeValue(j)
        parcel.writeValue(k)
        parcel.writeValue(l)
        parcel.writeValue(id)
        parcel.writeString(vehiclename)
        parcel.writeString(company)
        parcel.writeString(year)
        parcel.writeValue(totalimage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResponseResale> {
        override fun createFromParcel(parcel: Parcel): ResponseResale {
            return ResponseResale(parcel)
        }

        override fun newArray(size: Int): Array<ResponseResale?> {
            return arrayOfNulls(size)
        }
    }


}