package com.kadencelibrary.base

import android.os.Looper
import com.shopify.livedataktx.SingleLiveData


open class SLiveData <T> (val startValue: T? = null): SingleLiveData<T>() {


    open var vl : T?
        set(value) {

            if(Looper.myLooper() == Looper.getMainLooper()){
                this.value = value
            }else{
                super.postValue(value)
            }

        }
        get() {
            return this.value!!
        }


    fun update(){
        this.postValue(this.value)
    }

    fun reset(){
        this.postValue(startValue)
    }


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

    init {
        vl = startValue
    }
}