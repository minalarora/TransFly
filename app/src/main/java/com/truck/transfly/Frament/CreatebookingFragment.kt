package com.truck.transfly.Frament

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.truck.transfly.Model.RequestBooking
import com.truck.transfly.Model.ResponseVehicle
import com.truck.transfly.R
import com.truck.transfly.databinding.FragmentCreatebookingBinding
import com.truck.transfly.utils.ApiClient
import com.truck.transfly.utils.ApiEndpoints
import retrofit2.Retrofit


class CreatebookingFragment : DialogFragment() {


    var list = ArrayList<String>()
    private var vehicleList = ArrayList<ResponseVehicle>()
    lateinit var fragment: FragmentCreatebookingBinding
    lateinit var callback: CreateBookingListener
    private var retrofit: Retrofit? = null
    private var api: ApiEndpoints? = null
    private var adapter: ArrayAdapter<String>? = null

    var mineid: Int = 0
    var minename : String?= null
    var tyres: String? = null
    var trailor = false
    var loading: String?  = null
    var rate = 0
    var etl = 0


    interface CreateBookingListener {
        fun createBooking(requestBooking: RequestBooking)


    }

    fun setListener(callback: CreateBookingListener) {
        this.callback = callback
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mineid = it.getInt("mineid")
            minename = it.getString("minename")
            tyres = it.getString("tyres")
            trailor = it.getBoolean("trailor")
            loading = it.getString("loading")
            rate =it.getInt("rate")
            etl = it.getInt("etl")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_createbooking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragment = DataBindingUtil.bind<FragmentCreatebookingBinding>(view)!!
        retrofit = ApiClient.getRetrofitClient()
        if (retrofit != null) {
            api = retrofit!!.create(ApiEndpoints::class.java)
        }

        adapter = ArrayAdapter<String>(requireContext(),
                android.R.layout.simple_list_item_1, android.R.id.text1, list)

        fragment.registerCategory.adapter = adapter

        var t = ""
        if(trailor)
        {
            t = ""
        }
        else
        {
            t = "no"
        }
        fragment.text.text = "${minename} has ${tyres} tyres and ${t} trailor body allowed."

        fragment.otpSubmit.setOnClickListener {


            var vehicle = fragment.vehicle.text.toString()
            var driver = fragment.driver.text.toString()
            if (driver.isBlank() || driver.isEmpty()) {
                Toast.makeText(requireContext(), "Please add point of contact!", Toast.LENGTH_LONG).show()
                return@setOnClickListener;
            }
            if (vehicle.isBlank() || vehicle.isEmpty()) {
                Toast.makeText(requireContext(), "Please add vehicle number!", Toast.LENGTH_LONG).show()
                return@setOnClickListener;
            }

            var compressvehicle = vehicle.replace("\\s".toRegex(), "")
            if(vehicleList.isNotEmpty()){
            for (vehicleobj in vehicleList) {
                if (vehicleobj.number?.replace("\\s".toRegex(), "").equals(compressvehicle)) {
                    if (vehicleobj.active == true) {
                        //create booking with existing vehicle
                        val requestBooking = RequestBooking()
                        requestBooking.loading = loading
                        requestBooking.mineid = mineid
                        requestBooking.minename = minename
                        requestBooking.contact = fragment.driver.text.toString()
                        requestBooking.vehicle = compressvehicle
                        callback.createBooking(requestBooking)
                        this@CreatebookingFragment.dismiss()
                        return@setOnClickListener;
                    } else {
                        Toast.makeText(requireContext(), "This Vehicle is Already in booking!", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener;
                        //already in booking
                    }
                }
            }

                    val requestBooking = RequestBooking()
                    requestBooking.loading = loading
                    requestBooking.mineid = mineid
                    requestBooking.minename = minename
                    requestBooking.contact = fragment.driver.text.toString()
                    requestBooking.vehicle = compressvehicle
                    callback.createBooking(requestBooking)
                    this@CreatebookingFragment.dismiss()

                    //create booking with new vehicle

        }
            else
            {
                val requestBooking = RequestBooking()
                requestBooking.loading = loading
                requestBooking.mineid = mineid
                requestBooking.minename = minename
                requestBooking.contact = fragment.driver.text.toString()
                requestBooking.vehicle = compressvehicle
                callback.createBooking(requestBooking)
                this@CreatebookingFragment.dismiss()
            }
        }

        fragment.cancel.setOnClickListener {
            dismiss()
        }


        //do other stuff
    }

    fun getList(paralist: ArrayList<ResponseVehicle>)
    {
        vehicleList = paralist
        list = paralist.map { responseVehicle: ResponseVehicle -> responseVehicle.number } as ArrayList<String>
        Log.d("500",list.toString())
        adapter = ArrayAdapter<String>(requireContext(),
                R.layout.spinner_bookimg, R.id.text1, list)

        fragment.registerCategory.adapter = adapter
        fragment.registerCategory.onItemSelectedListener = object :AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                fragment.vehicle.setText(vehicleList[position].number)
                fragment.driver.setText(vehicleList[position].contact)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }

    override fun onResume() {
        var params = dialog?.window?.attributes
        params?.width = WindowManager.LayoutParams.MATCH_PARENT
        params?.height = WindowManager.LayoutParams.MATCH_PARENT
        dialog?.window?.attributes = params
        super.onResume()
    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CreatebookingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(mineid: Int, minename: String, tyres: String, trailor: Boolean, loading: String, rate: Int, etl: Int) = CreatebookingFragment()
                .apply {
                    arguments = Bundle().apply {

                        putInt("mineid", mineid)
                        putString("minename", minename)
                        putString("tyres", tyres)
                        putBoolean("trailor", trailor)
                        putString("loading", loading)
                        putInt("rate", rate)
                        putInt("etl", etl)
                    }
                }
//
//        intent.putExtra("mineid", responseMine.id)
//        intent.putExtra("minename", responseMine.name)
//        intent.putExtra("tyres", responseMine.tyres)
//        intent.putExtra("trailor", responseMine.trailer)
//        intent.putExtra("loading", loading)
//        intent.putExtra("rate", rate)
//        intent.putExtra("etl", etl)
    }
}
