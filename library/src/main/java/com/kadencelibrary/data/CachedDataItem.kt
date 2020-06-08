package com.kadencelibrary.data

import android.content.Context
import com.google.gson.reflect.TypeToken
import com.kadencelibrary.utils.CacheUtil


interface CacheLiveDataDelegate {
    fun getPrivateNameForFields(): String
}


open class CachedDataItem<T>(
    val context: Context,
    val delegate: CacheLiveDataDelegate?,
    val name: String,
    val startValue: T? = null,
    val ttype: TypeToken<T>? = null
) {


    val cache: CacheUtil = CacheUtil(context, delegate?.getPrivateNameForFields() ?: "DefaultCache")


    var value: T?
        set(value) {
            cache.save(value, loadTag())
        }
        get() {
            val pref = delegate?.getPrivateNameForFields()


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
        return "${delegate?.getPrivateNameForFields() ?: "none"}_$name\"".trim()
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
