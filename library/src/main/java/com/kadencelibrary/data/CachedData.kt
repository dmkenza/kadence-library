package com.kadencelibrary.data

import android.content.Context
import com.google.gson.reflect.TypeToken
import com.kadencelibrary.utils.CacheUtil
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


/**
 * Class help keep any data in shared preferences.
 */


open class CachedData<T>(
    private val context: Context,
    private val delegate: CacheDelegate?,
    private val name: String,
    private val startValue: T? = null,
    private val ttype: TypeToken<T>? = null
) {

    interface CacheDelegate {
        fun getSharedPrefencesFileName(): String
    }

    private val _flow = MutableStateFlow(startValue)
    val flow: StateFlow<T?> get() = _flow


    private val cache: CacheUtil = CacheUtil(context, delegate?.getSharedPrefencesFileName() ?: "DefaultCache")


    var value: T?
        set(value) {
            cache.save(value, loadTag())
            _flow.value = value
        }
        get() {
            val pref = delegate?.getSharedPrefencesFileName()

            val cached = cache.load<T>(loadTag(), ttype)

            if (cached == null) {
                vl = startValue
                return startValue
            }

            vl = cached
            return cached
        }


    var vl: T?
        set(value) {
            this.value = value

        }
        get() {
            return this.value
        }


    init {

        val tag = loadTag()

        val cached = cache.load<T>(tag, ttype)

        if (cached != null) {
            vl = cached
        } else {
            vl = startValue
        }

    }


    private fun loadTag(): String {
        return "${delegate?.getSharedPrefencesFileName() ?: "none"}_$name\"".trim()
    }




}
