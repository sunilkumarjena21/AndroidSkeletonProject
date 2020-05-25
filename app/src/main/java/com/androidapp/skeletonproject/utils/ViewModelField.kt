package com.androidapp.skeletonproject.utils

import androidx.databinding.BaseObservable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject
import java.io.Serializable

/**
 * Helper class in order to easily make ViewModel fields both Databinding observable, and able to
 * consume RxJava data
 */
data class ViewModelField<T>(private var mValue : T) : BaseObservable(), Consumer<T>, Serializable {

    companion object {
        const val serialVersionUID = 1L
    }

    private var onChange = PublishSubject.create<T>()
    /**
     * Value property for databinding notification
     */
    var value : T
        get() = this.mValue
        set(newValue) {
            if (newValue != mValue) {
                this.mValue = newValue
                notifyChange()
                onChange.onNext(newValue)
            }
        }

    /**
     * Update value with consumed data value
     */
    @Throws(Exception::class)
    override fun accept(newValue: T) {
        value = newValue
    }
}
