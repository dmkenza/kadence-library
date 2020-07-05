package com.kadencelibrary.extension

import android.content.Context
import android.content.Intent
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.badoo.mobile.util.WeakHandler
import java.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlin.collections.ArrayList

fun Any.isMainThread() = Looper.myLooper() == Looper.getMainLooper()


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


val Any.TAG0: String
    get() {
        return  if (!javaClass.isAnonymousClass) {
            val name = javaClass.simpleName
            // first 23 chars
            if (name.length <= 23) name else name.substring(0, 23)
        } else {
            val name = javaClass.name
            // last 23 chars
            if (name.length <= 23) name else name.substring(name.length - 23, name.length)
        }
    }


/**
 * Internal Contract to be implemented by ViewModel
 * Required to intercept and log ViewEvents
 */
interface ViewModelContract<EVENT> {
    fun process(viewEvent: EVENT)
}

interface ChildViewModelContract<EVENT> {
    /**
     * @return boolean - true if task need be processed by child view model.
     */
    fun process(viewEvent: EVENT): Boolean
}




/**
 * This is a custom NoObserverAttachedException and it does what it's name suggests.
 * Constructs a new exception with the specified detail message.
 * This is thrown, if you have not attached any observer to the LiveData.
 */
class NoObserverAttachedException(message: String) : Exception(message)



