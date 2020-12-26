package com.truck.transfly.utils

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager

class PreferenceUtil
{
    companion object
    {
        @JvmStatic
        fun putData(context: Context, key: String, value: String)
        {
            PreferenceManager.getDefaultSharedPreferences(context) ?.let {
                with(it.edit()){


                    putString(key,value)
                    apply()
                }
            }


        }

        @JvmStatic
        fun getData(context: Context,key: String?): String?
        {
            var value: String?= null
            PreferenceManager.getDefaultSharedPreferences(context)?.let {

               value = it.getString(key,null)
            }
            return value
        }
    }
}