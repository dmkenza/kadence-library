package com.kadencelibrary.data

import androidx.lifecycle.MutableLiveData
import java.util.*

/**
 * Improvement for MutableLiveData what allow obtain all item in queue with changing lifecycle.
 * For example if send 1,2,3,4 when no subscription and then get subscription [MutableLiveData] produce only 4,
 * [ActiveMutableLiveData] produce 1,2,3,4 in queue.
 */

class ActiveMutableLiveData<T> : MutableLiveData<T>() {

    private val values: Queue<T> = LinkedList()

    private var isActive: Boolean = false

    override fun onActive() {
        isActive = true
        while (values.isNotEmpty()) {
            setValue(values.poll())
        }
    }

    override fun onInactive() {
        isActive = false
    }

    override fun setValue(value: T) {
        if (isActive) {
            super.setValue(value)
        } else {
            values.add(value)
        }
    }
}