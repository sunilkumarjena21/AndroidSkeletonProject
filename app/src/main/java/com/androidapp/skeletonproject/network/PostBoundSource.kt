package com.androidapp.skeletonproject.network

import android.annotation.SuppressLint
import com.androidapp.skeletonproject.utils.AppResource
import io.reactivex.ObservableEmitter
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import quantum.airbus.com.mobile.disruption.errors.ApiError
import java.util.concurrent.TimeUnit

abstract class PostBoundSource<SavedType, RemoteType>(val emitter: ObservableEmitter<AppResource<SavedType>>) {

    private var delay = 0L

    /**
     * A disposable for remote data reactive stream
     */
    private val remoteDisposable: CompositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    fun startFetchingData() {
        /*if (BuildConfig.FLAVOR_datasource == "mock") {
            delay = 5000
        }*/

        //Secondly a network call is made and local data are updated if success
        //Remote data are fetched each side refresh subject is triggered
        //The remote disposable is cleared to dispose the previous stream if exist
        remoteDisposable.clear()

        remoteDisposable.add(
            getRemote()
                // A delay to mock the network response delay
                .delay(delay, TimeUnit.MILLISECONDS)
                .doOnError { error -> emitter.onNext(AppResource.fromError(error)) }
                .subscribeOn(Schedulers.io())
                .subscribe({ remoteData ->
                    if (remoteData.isSuccess && remoteData.data != null) {
                        saveCallResult(convertToBusinessEntity(remoteData.data!!))
                        emitter.onNext(
                            AppResource.fromData(convertToBusinessEntity(remoteData.data!!), null, false)
                        )
                    } else {
                        //If network call is a success but api return an error
                        //a result object with the error is emitted
                        emitter.onNext(
                            AppResource.fromError(
                                ApiError(
                                    calledService = calledService,
                                    statusCode = remoteData.code,
                                    technicalMessage = remoteData.message!!
                                )
                            )
                        )
                    }
                }
                    , { error ->
                        //On network error :
                        emitter.onNext(AppResource.fromError(error))
                    })
        )

    }

    abstract fun getRemote(): Single<BaseResponse<RemoteType>>

    abstract fun saveCallResult(data: SavedType)

    abstract fun convertToBusinessEntity(remoteType: RemoteType): SavedType

    abstract val calledService: String
}
