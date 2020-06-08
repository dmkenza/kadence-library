package com.kadencelibrary.extension.debug

import android.util.Log
import io.reactivex.exceptions.CompositeException

/**
 *  Wrapper for Logger.
 */

internal class Logger() {

    fun d(tag: String, msg: String, exception: Exception?) {
        Log.d(tag, msg, exception)
    }

    fun i(tag: String, msg: String, exception: Exception?) {
        Log.i(tag, msg, exception)
    }

    fun e(tag: String, msg: String, exception: Exception?) {

        if(exception?.cause is CompositeException){
            Log.e(tag, msg, (exception.cause as CompositeException ).exceptions.firstOrNull())
        }else{
            Log.e(tag, msg, exception)
        }
    }

    fun w(tag: String, msg: String, exception: Exception?) {

        if(exception?.cause is CompositeException){
            Log.w(tag, msg, (exception.cause as CompositeException ).exceptions.firstOrNull())
        }else{
            Log.w(tag, msg, exception)
        }
    }
}