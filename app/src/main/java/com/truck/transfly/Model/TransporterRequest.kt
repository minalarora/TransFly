package com.truck.transfly.Model

class TransporterRequest {

    var mineid: Int? = 0

    var minename: String? = null

    var loadingname: String ? =null

    var newrate: Int? = 0

    var oldrate: Int? = 0
    /*
    *  mineid:
    {
        type: Number,
        required: true,
        ref: 'Mine'
    },
     minename:{
         type: String,
         required: true
     },
     loadingname:{
         type: String,
         required: true
     },
     newrate:
     {
           type: Number,
            required: true
      },
    oldrate:
    {
        type: Number,
        required: true
    },
    requestuser:
    {
        type: String,
        required:true,
        ref: 'Transporter'
    },
    * */
}