package com.kadencelibrary.extension.debug

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.kadencelibrary.extension.text.toJson


/**
 * Short function for debuging
 */


fun Any.log(log: String = "", e: Exception? = null) {
    return LogWrapper.debug(log, e)
}

fun Any.wrg(log: String = "", e: Exception? = null) {
    return LogWrapper.warring(log, e)
}

fun Any.err(log: String = "", e: Exception? = null) {
    return LogWrapper.error(log, e)
}


fun Any.d(log: String = "", e: Exception? = null) {
    return LogWrapper.debug(log, e)
}

fun Any.e(log: String, e: Exception? = null) {
    return LogWrapper.error(log, e)
}

fun Any.e(log: String) {
    return LogWrapper.error(log, null)
}

fun Any.e(log: String, e: Throwable? = null) {
    return LogWrapper.error(
        log,
        java.lang.Exception(e)
    )
}

fun Any.w(log: String = "", e: Exception? = null) {
    return LogWrapper.warring(log, e)
}

fun Any.w(e: Exception) {
    return LogWrapper.warring("", e)
}

fun Any.w(e: Throwable) {

    val bundle = Bundle()
    bundle.putString("error", e.toJson())
    return LogWrapper.warring(
        "",
        Exception(e)
    )
}


fun Any.i(log: String, e: Exception? = null) {
    return LogWrapper.info(log, e)
}


fun Any.toast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
}


fun androidx.fragment.app.Fragment.toast(msg: String) {
    Toast.makeText(this.activity, msg, Toast.LENGTH_LONG).show()
}


fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}


/**
 * Class allow use logging with extra information such as number of code called debug function
 */

object LogWrapper {

    internal val logKit = Logger()

    fun debug(msg: String, exception: Exception?) {
        logKit?.d(
            location, msg, exception
        )
    }

    fun error(msg: String, exception: Exception?) {
        logKit?.e(
            location, msg, exception
        )
    }

    fun info(msg: String, exception: Exception?) {
        logKit?.i(
            location, msg, exception
        )
    }

    fun warring(msg: String, exception: Exception?) {
        logKit?.w(
            location, msg, exception
        )
    }


    /**
     *  Get a specific place in the code
     */

    internal val location: String
        get() {
            val className = LogWrapper::class.java.name
            val traces = Thread.currentThread().stackTrace
            var found = false

            for (i in traces.indices) {
                var trace = traces[i]

                try {
                    if (found) {
                        trace = traces[i + 3]

                        if (!trace.className.startsWith(className)) {
                            val clazz = Class.forName(trace.className)
                            return "[" + getClassName(
                                clazz
                            ) + ":" + trace.methodName + ":" + trace.lineNumber + "]: "
                        }
                    } else if (trace.className.startsWith(className)) {
                        found = true
                        continue
                    }
                } catch (e: ClassNotFoundException) {
                }

            }

            return "[]: "
        }


    internal fun getClassName(clazz: Class<*>?): String {
        return if (clazz != null) {
            if (!TextUtils.isEmpty(clazz.simpleName)) {
                clazz.simpleName
            } else getClassName(
                clazz.enclosingClass
            )

        } else ""

    }

}