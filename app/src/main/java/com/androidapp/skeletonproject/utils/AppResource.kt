package com.androidapp.skeletonproject.utils

open class AppResource<T>(val data: T?, val error: Throwable?, private val loading: Boolean = false ) {

    companion object {
        fun <T> fromData( data: T?, error: Throwable?, loading: Boolean ) : AppResource<T> {
            return AppResource(data, error, loading)
        }

        fun <T> fromError( error : Throwable) : AppResource<T> {
            return AppResource(null, error )
        }
    }

    fun isError() : Boolean{
        return error != null
    }

    fun isSuccess() : Boolean {
        return data != null
    }

    fun isLoading() : Boolean {
        return loading
    }
}
