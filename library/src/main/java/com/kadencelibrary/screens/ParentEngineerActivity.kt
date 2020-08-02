package com.kadencelibrary.screens

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kadencelibrary.R
import com.kadencelibrary.base.KadenceActivity
import kotlinx.android.synthetic.main.activity_debug.*

abstract class ParentEngineerActivity : KadenceActivity(), EngineerRecyclerAdapter.DebugRecyclerDelegate {


    private var debugItems: ArrayList<EngineerItem> = ArrayList()


    abstract fun createDebugActions(): List<EngineerItem>
//    fun createDebugActions(): List<DebugItem> {
//        debugItems.clear()
//        debugItems.add(DebugItem("qwe") {
//            toast("qwe")
//        })
//
//        return debugItems
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_debug)

        debugItems.clear()
        debugItems.addAll(createDebugActions())


        rv_debug.adapter = EngineerRecyclerAdapter(this)
        rv_debug.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onItemsRequested(): List<EngineerItem> {
        return debugItems
    }


}
