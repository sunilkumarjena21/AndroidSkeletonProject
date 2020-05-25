package com.androidapp.skeletonproject.network

import android.annotation.SuppressLint
import com.androidapp.skeletonproject.utils.AppResource
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import quantum.airbus.com.mobile.disruption.errors.ApiError
import java.util.concurrent.TimeUnit

/**
 * A class that emit data from local first, fetch new data from network, update database
 * and emit database data in an Observable
 */
abstract class NetworkBoundSource<SavedType, ReturnedType, RemoteType>(val emitter: ObservableEmitter<AppResource<ReturnedType>>) {

    private var delay = 0L

    /**
     * A disposable for local data reactive stream
     */
    private val localDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * A disposable for remote data reactive stream
     */
    private val remoteDisposable: CompositeDisposable = CompositeDisposable()

    @SuppressLint("CheckResult")
    fun startFetchingData(){
        /*if(BuildConfig.FLAVOR_datasource == "mock"){
            delay = 5000
        }*/

        //First current local are emited
        localDisposable.add(
            getLocal()
                .subscribeOn(Schedulers.io())
                .subscribe({ currentData ->
                    //On success, local data are emitted with loading to true
                    emitter.onNext(AppResource.fromData(currentData, null, true))
                }, {
                    //On failure, only the loading status is emitted
                    emitter.onNext(AppResource.fromData(null, null, true))
                })
        )

        //Secondly a network call is made and local data are updated if success
        //Remote data are fetched each side refresh subject is triggered
        refreshSubject.subscribe {
            //The remote disposable is cleared to dispose the previous stream if exist
            remoteDisposable.clear()

            remoteDisposable.add(
                getRemote()
                    // A delay to mock the network response delay
                    .delay(delay, TimeUnit.MILLISECONDS)
                    .doOnError { error -> emitter.onNext(AppResource.fromError(error)) }
                    .subscribeOn(Schedulers.io())
                    .subscribe({ remoteData ->
                        //We dispose of the first disposable in case it would complete after the second one
                        localDisposable.dispose()

                        if (remoteData.isSuccess && remoteData.data != null){
                            saveCallResult(convertToBusinessEntity(remoteData.data!!))
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
                        //On success we subscribe to database observation
                        observeDatabase()
                            .subscribeOn(Schedulers.io())
                            .subscribe { observableResult ->
                            emitter.onNext(
                                AppResource.fromData(
                                    data = observableResult,
                                    error = null,
                                    loading = false
                                )
                            )
                        }
                    }
                        , { error ->
                            //On network error, we though observe the database too
                            emitter.onNext(AppResource.fromError(error))
                            observeDatabase()
                                .subscribeOn(Schedulers.io())
                                .subscribe { observableResult ->
                                emitter.onNext(
                                    AppResource.fromData(
                                        data = observableResult,
                                        error = null,
                                        loading = false
                                    )
                                )
                            }
                        })
            )
        }
    }

    abstract fun getRemote(): Single<BaseResponse<RemoteType>>

    abstract fun getLocal(): Single<ReturnedType>

    abstract fun observeDatabase(): Observable<ReturnedType>

    abstract fun saveCallResult(data: SavedType)

    abstract fun convertToBusinessEntity(remoteType: RemoteType): SavedType

    abstract val refreshSubject: BehaviorSubject<Boolean>

    abstract val calledService: String

}

