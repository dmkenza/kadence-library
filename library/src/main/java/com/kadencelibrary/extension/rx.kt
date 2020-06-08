package com.kadencelibrary.extension

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.kadencelibrary.extension.debug.d
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.HashMap


/**
 * Short way using Rx function observeOn and subscribeOn
 */

/**
 * Use new thread for next action queue
 */
fun <T> Observable<T>.onNewThread(): Observable<T>  = this.observeOn(Schedulers.newThread())
fun <T> Single<T>.onNewThread(): Single<T>  = this.observeOn(Schedulers.newThread())
fun <T> Flowable<T>.onNewThread(): Flowable<T>  = this.observeOn(Schedulers.newThread())


/**
 * Use io thread for next action queue
 */
fun <T> Observable<T>.onIOThread(): Observable<T>  = this.observeOn(Schedulers.io())
fun <T> Single<T>.onIOThread(): Single<T>  = this.observeOn(Schedulers.io())
fun <T> Flowable<T>.onIOThread(): Flowable<T>  = this.observeOn(Schedulers.io())


//    val executor = Executors.newFixedThreadPool(30)
//    val pooledScheduler = Schedulers.from(executor)


/**
 * Use io thread for upper actions queue and Main for lower actions
 */

fun <T> Observable<T>.onCommonThreads(): Observable<T> = this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
fun <T> Single<T>.onCommonThreads(): Single<T> = this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
fun <T> Flowable<T>.onCommonThreads(): Flowable<T> = this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
fun  Completable.onCommonThreads(): Completable = this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())


/**
 * Use Main thread for lower actions
 */

fun <T> Observable<T>.onMainThread(): Observable<T> = this.observeOn(AndroidSchedulers.mainThread())
fun <T> Single<T>.onMainThread(): Single<T> = this.observeOn(AndroidSchedulers.mainThread())
fun <T> Flowable<T>.onMainThread(): Flowable<T> = this.observeOn(AndroidSchedulers.mainThread())



///**
// *  notify when loading begin and end
// */
//
//fun <T> Single<T>.withLoading(vm: BaseVm): Single<T> {
//    return this.doOnSubscribe {
//        vm.loadingSingleLifeData.value = true
//    }.doFinally {
//        vm.loadingSingleLifeData.value = true
//    }.doOnError {
//        vm.errorSingleLifeData.value = it
//    }.onCommonThreads()
//}

/** create merged livedata from many livedata */

fun merge(vararg liveItems: LiveData<*>): LiveData<MutableList<Any?>> {

    return MediatorLiveData<MutableList<Any?>>().apply {

        val zippedObjects = HashMap<Any, Any?>()

        liveItems.forEach {

            d()
            addSource(it) { item ->

                // if (!zippedObjects.contains(item?.toString() ?: "")) {
                zippedObjects.put(it, item)
                //  }
                if (zippedObjects.size >= liveItems.size) {
                    value = zippedObjects.values.toMutableList()
                }
            }

        }
    }
}