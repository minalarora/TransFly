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

    var vehicleOwner = RequestVehicleOwner()
    var responseVehicleOwner = ResponseVehicleOwner()

    var transporterOwner = RequestTransporter()
    var responseTransporterOwner = ResponseTransporter()

    var fieldStaff = RequestFieldStaff()
    var responseFieldStaff = ResponseFieldStaff()

    var areaManager = RequestAreaManager()
    var responseAreaManager = ResponseAreaManager()
}