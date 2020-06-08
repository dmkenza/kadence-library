package com.kadencelibrary.base

import android.content.Context
import com.kadencelibrary.base.KadenceVm


/**
 * ViewModel for complex fragment and view
 * This Vm must be in BaseVm and obtained fragment by ViewModelHolder
 */
open class KadenceModel (open val vm : KadenceVm) {


//    val  dataRepo by lazy {
//        vm.dataRepo
//    }


    val infoSingleLifeData by lazy { vm.infoSingleLifeData }
    val loadingSingleLifeData by lazy { vm.loadingSingleLifeData }
    val errorSingleLifeData  by lazy { vm.errorSingleLifeData }


//    var context : Context = CoreLibrary.comp.context
//    var nav : AppNavigator = CoreLibrary.comp.nav


    val disposables  by lazy { vm.disposables }


}