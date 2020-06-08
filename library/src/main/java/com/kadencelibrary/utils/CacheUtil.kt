package com.kadencelibrary.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kadencelibrary.extension.debug.w

/**
 * simply hold any data, may set expiration date and check it
 * @param context Context is required.
 * @param sharedPreferenceName name of SharedPreference file.
 */

class CacheUtil constructor(val context: Context, sharedPreferenceName: String) {

    var mPrefs: SharedPreferences =
        context.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)

    val gson = Gson()


    /**
     *  Save any object to shared preferences.
     */

    fun <T> save(x: T, tag: String) {

        val prefsEditor = mPrefs.edit()
        val json = gson.toJson(x)

        prefsEditor.putString(tag, json)
        prefsEditor.apply()
    }

    /**
     *  Load object from SharedPreferences.
     */

    fun <T> load(tag: String, type: TypeToken<T>? = null): T? {
        try {
            val json = mPrefs.getString(tag, " ")
            var result: T

            if (type == null) {
                var type = object : TypeToken<T>() {}
                result = Gson().fromJson(json, type.getType()) as T
            } else {
                result = Gson().fromJson(json, type.getType()) as T
            }



            return result;

        } catch (e: Exception) {
            w("", e)
        }

        return null
    }


}