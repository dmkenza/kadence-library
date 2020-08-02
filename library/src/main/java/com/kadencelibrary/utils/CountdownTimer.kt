package com.kadencelibrary.utils

import com.kadencelibrary.extension.debug.log
class CountdownTimer (val startImmediately : Boolean = true ){


    private var start  = 0L
    private var checkpoint = 0L

    init {

        if(startImmediately){
            start()
        }
    }

    fun start(){
        start = System.currentTimeMillis()
        checkpoint = start

    }


    fun check(msg: String){
        val now  = System.currentTimeMillis()
        val delta = now - checkpoint
        checkpoint = now

        log("$msg delta = $delta")
    }

}