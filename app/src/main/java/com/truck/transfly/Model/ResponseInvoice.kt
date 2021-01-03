package com.truck.transfly.Model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class ResponseInvoice() : Parcelable {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("minename")
    var minename: String? = null

    @SerializedName("loading")
    var loading: String? = null

    @SerializedName("status")
    var status: String? = null

    @SerializedName("vehicleowner")
    var vehicleOwnerName: String? = null

    @SerializedName("tonnage")
    var tonnage: Int? = null

    @SerializedName("rate")
    var rate : Int?  = null

    @SerializedName("amount")
    var amount : Int?  = null

    @SerializedName("hsd")
    var hsd : Int?  = null

    @SerializedName("cash")
    var cash : Int?  = null

    @SerializedName("vehicleownermobile")
    var vehicleownermobile: String? = null

    @SerializedName("vehicle")
    var vehiclenumber: String? = null

    @SerializedName("tds")
    var tds : Int?  = null

    @SerializedName("officecharge")
    var officecharge : Int?  = null

    @SerializedName("shortage")
    var shortage : Int?  = null

    @SerializedName("balanceamount")
    var balanceamount : Int?  = null

    @SerializedName("challantotransporter")
    var challanToTransporter : String?  = null

    @SerializedName("balanceamountcleared")
    var balanceAmountCleared : String?  = null

    @SerializedName("date")
    var date : String?  = null

    @SerializedName("transporter")
    var transporterMobile: String?  =null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        minename = parcel.readString()
        loading = parcel.readString()
        status = parcel.readString()
        vehicleOwnerName = parcel.readString()
        tonnage = parcel.readValue(Int::class.java.classLoader) as? Int
        rate = parcel.readValue(Int::class.java.classLoader) as? Int
        amount = parcel.readValue(Int::class.java.classLoader) as? Int
        hsd = parcel.readValue(Int::class.java.classLoader) as? Int
        cash = parcel.readValue(Int::class.java.classLoader) as? Int
        vehicleownermobile = parcel.readString()
        vehiclenumber = parcel.readString()
        tds = parcel.readValue(Int::class.java.classLoader) as? Int
        officecharge = parcel.readValue(Int::class.java.classLoader) as? Int
        shortage = parcel.readValue(Int::class.java.classLoader) as? Int
        balanceamount = parcel.readValue(Int::class.java.classLoader) as? Int
        challanToTransporter = parcel.readString()
        balanceAmountCleared = parcel.readString()
        date = parcel.readString()
        transporterMobile = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(minename)
        parcel.writeString(loading)
        parcel.writeString(status)
        parcel.writeString(vehicleOwnerName)
        parcel.writeValue(tonnage)
        parcel.writeValue(rate)
        parcel.writeValue(amount)
        parcel.writeValue(hsd)
        parcel.writeValue(cash)
        parcel.writeString(vehicleownermobile)
        parcel.writeString(vehiclenumber)
        parcel.writeValue(tds)
        parcel.writeValue(officecharge)
        parcel.writeValue(shortage)
        parcel.writeValue(balanceamount)
        parcel.writeString(challanToTransporter)
        parcel.writeString(balanceAmountCleared)
        parcel.writeString(date)
        parcel.writeString(transporterMobile)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ResponseInvoice> {
        override fun createFromParcel(parcel: Parcel): ResponseInvoice {
            return ResponseInvoice(parcel)
        }

        override fun newArray(size: Int): Array<ResponseInvoice?> {
            return arrayOfNulls(size)
        }
    }


}