package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class ResponseTransporter {

   // @SerializedName("profile")
    var profile: String? = null

   // @SerializedName("id")
    var id: String? = null

    //@SerializedName("name")
    var  name: String? = null;

   // @SerializedName("mobile")
    var mobile: String? = null;

   // @SerializedName("email")
    var email: String? = null

   // @SerializedName("token")
    var token: String? = null

    //@SerializedName("status")
    var status: Int? = null

    //@SerializedName("gst")
    var gst: String? = null

    //@SerializedName("sta")
    var sta: String? = null

   // @SerializedName("aadhaar")
    var aadhaar: String? = null

   // @SerializedName("mininglicense")
    var mininglicense: String? = null

    //@SerializedName("ename")
    var ename: String? = null

    //@SerializedName("erelation")
    var erelation: String? = null

   // @SerializedName("emobile")
    var emobile: String? = null

    @SerializedName("pan")
    var pan: String?  = null
    override fun toString(): String {
        return "ResponseTransporter(name=$name, mobile=$mobile, email=$email, status=$status, gst=$gst, sta=$sta, aadhaar=$aadhaar, mininglicense=$mininglicense, pan=$pan)"
    }


}