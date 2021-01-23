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
    var tonnage: Double? = null

    @SerializedName("rate")
    var rate : Double?  = null

    @SerializedName("amount")
    var amount : Double?  = null

    @SerializedName("hsd")
    var hsd : Double?  = null

    @SerializedName("cash")
    var cash : Double?  = null

    @SerializedName("vehicleownermobile")
    var vehicleownermobile: String? = null

    @SerializedName("vehicle")
    var vehiclenumber: String? = null

    @SerializedName("tds")
    var tds : Double?  = null

    @SerializedName("officecharge")
    var officecharge : Double?  = null

    @SerializedName("shortage")
    var shortage : Double?  = null

    @SerializedName("balanceamount")
    var balanceamount : Double?  = null

    @SerializedName("challantotransporter")
    var challanToTransporter : String?  = null

    @SerializedName("balanceamountcleared")
    var balanceAmountCleared : String?  = null

    @SerializedName("date")
    var date : String?  = null

    @SerializedName("transporter")
    var transporterMobile: String?  =null

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Double::class.java.classLoader) as? Int
        minename = parcel.readString()
        loading = parcel.readString()
        status = parcel.readString()
        vehicleOwnerName = parcel.readString()
        tonnage = parcel.readValue(Double::class.java.classLoader) as? Double
        rate = parcel.readValue(Double::class.java.classLoader) as? Double
        amount = parcel.readValue(Double::class.java.classLoader) as? Double
        hsd = parcel.readValue(Double::class.java.classLoader) as? Double
        cash = parcel.readValue(Double::class.java.classLoader) as? Double
        vehicleownermobile = parcel.readString()
        vehiclenumber = parcel.readString()
        tds = parcel.readValue(Double::class.java.classLoader) as? Double
        officecharge = parcel.readValue(Double::class.java.classLoader) as? Double
        shortage = parcel.readValue(Double::class.java.classLoader) as? Double
        balanceamount = parcel.readValue(Double::class.java.classLoader) as? Double
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