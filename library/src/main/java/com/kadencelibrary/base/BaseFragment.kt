package com.kadencelibrary.base

import androidx.lifecycle.ViewModelProvider
import io.reactivex.disposables.CompositeDisposable

open class BaseFragment : androidx.fragment.app.Fragment (){

    val disposable = CompositeDisposable()

//    val  dataRepo = CoreLibrary.comp.dataRepo
//
//
//    val spotlightHelper : SpotlightHelper by lazy {
//        (activity as KadenceActivity).spotlightHelper
//    }
//
//    var nav : AppNavigator = CoreLibrary.comp.nav


    fun <T : KadenceVm> getViewModel(clazz: Class<T>): T {

        if(context is KadenceActivity){
            return ViewModelProvider(this)[clazz]
        }

        else {// if(context is BaseActivity){
            return ViewModelProvider(this)[clazz]
        }

    }


//    fun <T : KadenceModel> getInnerViewModel(clazz: Class<T>): T {
//         return (context as? ViewModelHolder<T>)?.onViewModelRequested()
//                ?: (parentFragment as? ViewModelHolder<T>)?.onViewModelRequested()
//                        ?: throw  Exception("ViewModelHolder<${clazz.name}> must be implemented in activity or parentFragment")
//    }

    fun getBaseActivity(): KadenceActivity? {
        return  activity as? KadenceActivity
    }


//    fun getModelFactory(): ViewModelProvider.Factory? {
//        return  (context as? BaseActivity)?.modelFactory
//    }


    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }


}