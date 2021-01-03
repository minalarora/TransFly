package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class ResponseResale {
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














}