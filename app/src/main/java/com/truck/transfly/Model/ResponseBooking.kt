package com.truck.transfly.Model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class ResponseBooking() : Parcelable {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("loading")
    var loading: String? = null

    @SerializedName("status")
    var status: String? = null

    @SerializedName("mineid")
    var mineid: Int? = null

    @SerializedName("minename")
    var minename: String? = null

    @SerializedName("createdAt")
    var date: String? = null

    @SerializedName("vehicle")
    var vehiclename: String? = null

    @SerializedName("vehicleowner")
    var vehicleowner: String? = null

    @SerializedName("vehicleownermobile")
    var vehicleownermobile: String? = null

    @SerializedName("owner")
    var owner: String? = null

    @SerializedName("contact")
    var contact: String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        loading = parcel.readString()
        status = parcel.readString()
        mineid = parcel.readValue(Int::class.java.classLoader) as? Int
        minename = parcel.readString()
        date = parcel.readString()
        vehiclename = parcel.readString()
        vehicleowner = parcel.readString()
        vehicleownermobile = parcel.readString()
        owner = parcel.readString()
        contact=parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(loading)
        parcel.writeString(status)
        parcel.writeValue(mineid)
        parcel.writeString(minename)
        parcel.writeString(date)
        parcel.writeString(vehiclename)
        parcel.writeString(vehicleowner)
        parcel.writeString(vehicleownermobile)
        parcel.writeString(owner)
        parcel.writeString(contact)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResponseBooking> {
        override fun createFromParcel(parcel: Parcel): ResponseBooking {
            return ResponseBooking(parcel)
        }

        override fun newArray(size: Int): Array<ResponseBooking?> {
            return arrayOfNulls(size)
        }
    }

}