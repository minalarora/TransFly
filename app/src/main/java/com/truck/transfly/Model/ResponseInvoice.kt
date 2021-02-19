package com.truck.transfly.Model

import android.os.Parcel
import android.os.Parcelable

class ResponseInvoice() : Parcelable {
   // @SerializedName("id")
    var id: Int? = null

    //@SerializedName("minename")
    var minename: String? = null

    //@SerializedName("transportername")
    var transportername: String? = null

    //@SerializedName("modeofpayment")
    var modeofpayment: String? = null

    //@SerializedName("loading")
    var loading: String? = null

    //@SerializedName("status")
    var status: String? = null

    //@SerializedName("vehicleowner")
    var vehicleowner: String? = null

    //@SerializedName("tonnage")
    var tonnage: Double? = null

    //@SerializedName("rate")
    var rate : Double?  = null

    //@SerializedName("amount")
    var amount : Double?  = null

    //@SerializedName("hsd")
    var hsd : Double?  = null

    //@SerializedName("cash")
    var cash : Double?  = null

    //@SerializedName("vehicleownermobile")
    var vehicleownermobile: String? = null

    //@SerializedName("vehicle")
    var vehicle: String? = null

    //@SerializedName("tds")
    var tds : Double?  = null

   // @SerializedName("officecharge")
    var officecharge : Double?  = null

   // @SerializedName("shortage")
    var shortage : Double?  = null

   // @SerializedName("balanceamount")
    var balanceamount : Double?  = null

    //@SerializedName("challantotransporter")
    var challantotransporter: String?  = null

   // @SerializedName("balanceamountcleared")
    var balanceamountcleared: String?  = null

    //@SerializedName("date")
    var date : String?  = null

   // @SerializedName("transporter")
    var transporter: String?  =null

    //@SerializedName("contact")
    var contact : String?=null

    //@SerializedName("transporteramount")
    var transporteramount: Double?= 0.0

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        minename = parcel.readString()
        transportername = parcel.readString()
        modeofpayment = parcel.readString()
        loading = parcel.readString()
        status = parcel.readString()
        vehicleowner = parcel.readString()
        tonnage = parcel.readValue(Double::class.java.classLoader) as? Double
        rate = parcel.readValue(Double::class.java.classLoader) as? Double
        amount = parcel.readValue(Double::class.java.classLoader) as? Double
        hsd = parcel.readValue(Double::class.java.classLoader) as? Double
        cash = parcel.readValue(Double::class.java.classLoader) as? Double
        vehicleownermobile = parcel.readString()
        vehicle = parcel.readString()
        tds = parcel.readValue(Double::class.java.classLoader) as? Double
        officecharge = parcel.readValue(Double::class.java.classLoader) as? Double
        shortage = parcel.readValue(Double::class.java.classLoader) as? Double
        balanceamount = parcel.readValue(Double::class.java.classLoader) as? Double
        challantotransporter = parcel.readString()
        balanceamountcleared = parcel.readString()
        date = parcel.readString()
        transporter = parcel.readString()
        contact = parcel.readString()
        transporteramount = parcel.readValue(Double::class.java.classLoader) as? Double
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(minename)
        parcel.writeString(transportername)
        parcel.writeString(modeofpayment)
        parcel.writeString(loading)
        parcel.writeString(status)
        parcel.writeString(vehicleowner)
        parcel.writeValue(tonnage)
        parcel.writeValue(rate)
        parcel.writeValue(amount)
        parcel.writeValue(hsd)
        parcel.writeValue(cash)
        parcel.writeString(vehicleownermobile)
        parcel.writeString(vehicle)
        parcel.writeValue(tds)
        parcel.writeValue(officecharge)
        parcel.writeValue(shortage)
        parcel.writeValue(balanceamount)
        parcel.writeString(challantotransporter)
        parcel.writeString(balanceamountcleared)
        parcel.writeString(date)
        parcel.writeString(transporter)
        parcel.writeString(contact)
        parcel.writeValue(transporteramount)
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