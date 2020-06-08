package com.kadencelibrary.base

import android.app.Dialog
import androidx.lifecycle.ViewModelProviders
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import io.reactivex.disposables.CompositeDisposable

open class BaseDialog : androidx.fragment.app.DialogFragment() {


//    var nav: AppNavigator = CoreLibrary.comp.nav
//
//    val  dataRepo = CoreLibrary.comp.dataRepo




    val disposables = CompositeDisposable()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        disposables.dispose()
        disposables.clear()
        super.onDestroy()
    }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return dialog
    }


    fun <T : KadenceVm> getViewModel(clazz: Class<T>): T {

        if(context is KadenceActivity){
            return  ViewModelProviders.of((  context as KadenceActivity))[clazz]
        }

        else {// if(context is BaseActivity){
            return  ViewModelProviders.of((  context as KadenceActivity))[clazz]
        }
    }



//    fun getModelFactory(): ViewModelProvider.Factory? {
//        return  (context as? BaseActivity)?.modelFactory
//    }



//    fun getLiveData(): MLiveData? {
//        val  vm = ViewModelProviders.of((  context as BaseActivity), getModelFactory())[BaseVm::class.java]
//        return vm.getLiveData()
//    }

}