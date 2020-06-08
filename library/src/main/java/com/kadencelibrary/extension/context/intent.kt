package com.kadencelibrary.extension.context

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.kadencelibrary.extension.text.fromJson


/**
 * Simple way to get data from intent for activities and fragments.
 *
 * Example using:
 * val item: Data by extraNotNull(KEY, DEFAULT_VALUE).
 */

inline fun <reified T : Any> Intent?.extra(key: String, default: T? = null): T? {
    val value = this?.extras?.get(key)


    when (T::class.java) {
        Int::class.java, Boolean::class.java, Long::class.java, String::class.java -> {
            return if (value is T) value else default
        }
        else -> {
            return if (value != null) {
                Gson().fromJson<T>(value as String)
            } else {
                default
            }
        }
    }

}


inline fun <reified T : Any> Activity.extra(key: String, default: T? = null) = lazy {
    val value = intent?.extras?.get(key)

    when (T::class.java) {
        Int::class.java, Boolean::class.java, Long::class.java, String::class.java,
        Int::class.javaObjectType, Boolean::class.javaObjectType, Long::class.javaObjectType, String::class.javaObjectType -> {
            if (value is T) value else default
        }
        else -> {
            if (value != null) {
                Gson().fromJson<T>(value as String)
            } else {
                default
            }
        }
    }
}


inline fun <reified T : Any> Activity.extraNotNull(key: String, default: T? = null) = lazy {
    val value = intent?.extras?.get(key)


    val x = when (T::class.java) {
        Int::class.java, Boolean::class.java, Long::class.java, String::class.java,
        Int::class.javaObjectType, Boolean::class.javaObjectType, Long::class.javaObjectType, String::class.javaObjectType -> {
            if (value is T) value else default
        }
        else -> {
            if (value != null) {
                Gson().fromJson<T>(value as String)
            } else {
                default
            }
        }
    }

    requireNotNull(x)
}




inline fun <reified T : Any> Fragment.extra(key: String, default: T? = null) = lazy {
    val value = arguments?.get(key)
    val x = when (T::class.java) {
        Int::class.java, Boolean::class.java, Long::class.java, String::class.java,
        Int::class.javaObjectType, Boolean::class.javaObjectType, Long::class.javaObjectType, String::class.javaObjectType -> {
            if (value is T) value else default
        }
        else -> {
            if (value != null) {
                Gson().fromJson<T>(value as String)
            } else {
                default
            }
        }
    }
}


inline fun <reified T : Any> Fragment.extraNotNull(key: String, default: T? = null) = lazy {
    val value = arguments?.get(key)

    val x = when (T::class.java) {
        Int::class.java, Boolean::class.java, Long::class.java, String::class.java,
        Int::class.javaObjectType, Boolean::class.javaObjectType, Long::class.javaObjectType, String::class.javaObjectType -> {
            if (value is T) value else default
        }
        else -> {
            if (value != null) {
                Gson().fromJson<T>(value as String)
            } else {
                default
            }
        }
    }


    requireNotNull(x)
}


fun Intent.putExtra(vararg params: Pair<String, Any>) {

    params.forEach {
        if (it.second is Int) {
            this.putExtra(it.first, it.second as Int)
        } else if (it.second is Boolean) {
            this.putExtra(it.first as String, it.second as Boolean)
        } else if (it.second is Long) {
            this.putExtra(it.first as String, it.second as Long)
        } else if (it.second is String) {
            this.putExtra(it.first as String, it.second as String)
        } else if (it.second is List<*> || it.second is Set<*>) {
            val json = Gson().toJson(it.second)
            this.putExtra(it.first as String, json)
        } else {
            val json = Gson().toJson(it.second)
            this.putExtra(it.first as String, json)
        }


    }
}


fun Context.openActivity(clazz: Class<out Activity>) {
    val i = Intent(this, clazz)
    i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP)
    this.startActivity(i)
}




