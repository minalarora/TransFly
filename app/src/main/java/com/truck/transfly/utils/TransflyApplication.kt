package com.truck.transfly.utils

import android.app.Application
import com.truck.transfly.Model.*
import net.gotev.uploadservice.BuildConfig
import net.gotev.uploadservice.UploadService

class TransflyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID

    }


    var responseVehicleOwner = ResponseVehicleOwner()


    var responseTransporterOwner = ResponseTransporter()


    var responseFieldStaff = ResponseFieldStaff()


    var responseAreaManager = ResponseAreaManager()
}