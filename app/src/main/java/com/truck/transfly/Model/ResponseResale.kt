package com.truck.transfly.Model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class ResponseResale() : Parcelable {
    @SerializedName("1")
    var ownership: Boolean? = false;

    @SerializedName("2.")
    var insurancepapers: Boolean? = false;

    @SerializedName("3")
    var checkAndClearances: Boolean? = false;

    @SerializedName("4")
    var roadTaxReceipt: Boolean? = false;

    @SerializedName("5")
    var vehicleServicesBook: Boolean? = false;

    @SerializedName("6")
    var loanClearanceCheck: Boolean? = false;

    @SerializedName("7")
    var pollutionCertificate: Boolean? = false;

    @SerializedName("8")
    var engineAndChassisCheck: Boolean? = false;

    @SerializedName("9")
    var body: Boolean? = false;

    @SerializedName("10")
    var cabin: Boolean? = false;

    @SerializedName("11")
    var tyres: Boolean? = false;

    @SerializedName("12")
    var otherImportantDeclarations: Boolean? = false;

    @SerializedName("id")
    var id: Int? = 0

    @SerializedName("vehiclename")
    var vehicleName: String?= null;

    @SerializedName("company")
    var company: String?= null;

    @SerializedName("year")
    var year: String?= null;

    @SerializedName("totalimage")
    var totalImage: Int?= 0;

    var imageList: ArrayList<String>? = ArrayList()

    constructor(parcel: Parcel) : this() {
        ownership = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        insurancepapers = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        checkAndClearances = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        roadTaxReceipt = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        vehicleServicesBook = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        loanClearanceCheck = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        pollutionCertificate = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        engineAndChassisCheck = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        body = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        cabin = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        tyres = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        otherImportantDeclarations = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        vehicleName = parcel.readString()
        company = parcel.readString()
        year = parcel.readString()
        totalImage = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(ownership)
        parcel.writeValue(insurancepapers)
        parcel.writeValue(checkAndClearances)
        parcel.writeValue(roadTaxReceipt)
        parcel.writeValue(vehicleServicesBook)
        parcel.writeValue(loanClearanceCheck)
        parcel.writeValue(pollutionCertificate)
        parcel.writeValue(engineAndChassisCheck)
        parcel.writeValue(body)
        parcel.writeValue(cabin)
        parcel.writeValue(tyres)
        parcel.writeValue(otherImportantDeclarations)
        parcel.writeValue(id)
        parcel.writeString(vehicleName)
        parcel.writeString(company)
        parcel.writeString(year)
        parcel.writeValue(totalImage)
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