package com.kadencelibrary.extension

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.badoo.mobile.util.WeakHandler
import java.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.collections.ArrayList


fun <E> List<E>.random(random: java.util.Random = Random()): E? = if (size > 0) get(random.nextInt(size)) else null

fun <E> List<E>?.toArrayList(): ArrayList<E> = ArrayList(this ?: ArrayList())


inline fun Any.postDelay(delay: Int = -1, crossinline func: () -> Unit) {

    var delay = delay

    if (delay <= -1) {
        delay = 0
    }

    val handler = WeakHandler()
    handler.postDelayed({
        func()
    }, delay.toLong())
}

//    val scale: Float
//        get() {
//            return CoreLibrary.comp.context.resources.getDisplayMetrics().density
//        }

//fun Int.toDp(): Int = (this * Help.scale + 0.5f).toInt()



