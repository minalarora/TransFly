package com.truck.transfly.Model

import com.google.gson.annotations.SerializedName

class ResponseAreaManager {

    var id: String? = null


    var  name: String? = null;


    var profile: String? = null


    var mobile: String? = null;


    var email: String? = null


    var bankpersonname: String? = null


    var status: Int? = null
    //0  kyc not
    //1  kyc pending
    //2  comp;


    var accountno: String? = null

    //@SerializedName("ifsc")
    var ifsc: String? = null

   // @SerializedName("bankname")
    var bankname: String? = null

    //@SerializedName("pan")
    var pan: String? = null

    //@SerializedName("aadhaar")
    var aadhaar: String? = null

    //@SerializedName("ename")
    var ename: String? = null

    //@SerializedName("erelation")
    var erelation: String? = null

    //@SerializedName("emobile")
    var emobile: String? = null

    //@SerializedName("token")
    var token: String? = null
    override fun toString(): String {
        return "ResponseAreaManager(name=$name, mobile=$mobile, email=$email, status=$status, accountno=$accountno, ifsc=$ifsc, bankname=$bankname, pan=$pan, aadhaar=$aadhaar, token=$token)"
    }


}