package com.kadencelibrary.screens

import android.view.View
import com.kadencelibrary.R
import com.kadencelibrary.base.KadenceRecyclerAdapter
import kotlinx.android.synthetic.main.item_debug.view.*

class EngineerRecyclerAdapter(val delegate: DebugRecyclerDelegate) :
    KadenceRecyclerAdapter<EngineerItem>() {


    interface DebugRecyclerDelegate {
        fun onItemsRequested(): List<EngineerItem>
    }

    override var data: List<EngineerItem>
        get() = delegate.onItemsRequested()
        set(value) {}


    override var layout_main_id: Int
        get() = R.layout.item_debug
        set(value) {}

    override fun getViewHolder(view: View, viewType: Int): BaseRViewHolder {

        return object : BaseRViewHolder(view) {
            override fun bind(item: EngineerItem) {
                view.apply {
                    bt.setText(item.title)

                    bt.setOnClickListener {
                        item.funct.invoke()
                    }
                }

            }
        }
    }

}