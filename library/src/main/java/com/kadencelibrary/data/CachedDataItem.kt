package com.kadencelibrary.data

import android.content.Context
import com.google.gson.reflect.TypeToken
import com.kadencelibrary.extension.debug.d
import com.kadencelibrary.utils.CacheUtil


// todo


open class CachedDataItem<T>(
    val context: Context,
    val delegate: CacheDelegate?,
    val name: String,
    val startValue: T? = null,
    val ttype: TypeToken<T>? = null
) {

    interface CacheDelegate {
        fun getSharedPrefencesFileName(): String
    }


    val cache: CacheUtil =
        CacheUtil(context, delegate?.getSharedPrefencesFileName() ?: "DefaultCache")


    var value: T?
        set(value) {
            cache.save(value, loadTag())
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


    fun getCachedValue(): T? {


        val cached = cache.load<T>(loadTag(), ttype)


        if (cached == null) {
            vl = startValue
            return startValue
        }

        vl = cached
        return cached

    }


}
