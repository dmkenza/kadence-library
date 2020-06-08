package com.kadencelibrary.base

import androidx.lifecycle.MutableLiveData
import android.os.Looper

open class MLiveData<T>(open val startValue: T? = null) : MutableLiveData<T>() {


    open var vl: T?
        set(value) {

            if (Looper.myLooper() == Looper.getMainLooper()) {
                this.value = value
            } else {
                super.postValue(value)
            }

        }
        get() {
            return this.value!!
        }


    fun notifyDataChanged() = this.postValue(this.value)



    @Suppress("UNCHECKED_CAST")
    override fun getValue(): T? {
        return super.getValue()
    }
    @Suppress("RedundantOverride")
    override fun setValue(value: T?) {

        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.setValue(value)
        } else {
            super.postValue(value)
        }
    }

    @Suppress("RedundantOverride")
    override fun postValue(value: T?) {
        super.postValue(value)
    }


    init {
        vl = startValue
    }
}



