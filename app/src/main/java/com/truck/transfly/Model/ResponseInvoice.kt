package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class ResponseInvoice {
    @SerializedName("id")
    var id: Int? = null

    @SerializedName("minename")
    var minename: String? = null

    @SerializedName("loading")
    var loading: String? = null

    @SerializedName("status")
    var status: String? = null

    @SerializedName("vehicleownername")
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



}