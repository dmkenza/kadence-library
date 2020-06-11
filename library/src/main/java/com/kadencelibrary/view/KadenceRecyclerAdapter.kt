package com.kadencelibrary.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.extensions.LayoutContainer

abstract class KadenceRecyclerAdapter<T>() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    abstract var data: List<T>
    abstract var layout_main_id: Int

    val disposables = CompositeDisposable()


    abstract inner class BaseRViewHolder(var view: View) : RecyclerView.ViewHolder(view), Binder<T>,
        LayoutContainer {
        abstract override fun bind(item: T)


        val context: Context?
            get() = view.context


        override val containerView: View?
            get() = view
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRViewHolder {


        return getViewHolder(
            LayoutInflater.from(parent.context).inflate(
                layout_main_id,
                parent,
                false
            ), viewType
        )

    }


    abstract fun getViewHolder(view: View, viewType: Int): BaseRViewHolder


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Binder<T>).bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }


    internal interface Binder<T> {
        fun bind(data: T)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        disposables.clear();
        disposables.dispose();
    }

    companion object {

    }

}