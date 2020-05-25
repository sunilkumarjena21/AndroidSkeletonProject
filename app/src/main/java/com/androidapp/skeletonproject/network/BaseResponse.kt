package com.androidapp.skeletonproject.network

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("code")
    var code: Int = 0,

    @SerializedName("message")
    var message: String? = null,

    @SerializedName("data")
    var data: T? = null
) {

    companion object {
        /**
         * States of the api request.
         */
        const val SUCCESS = 200
    }


    /***********
     * METHODS *
     */
    val isSuccess: Boolean
        get() = code == SUCCESS

}
