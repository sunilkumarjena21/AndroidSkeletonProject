package com.androidapp.skeletonproject.services

import com.androidapp.skeletonproject.model.apientity.UserApiEntity
import com.androidapp.skeletonproject.network.BaseResponse
import io.reactivex.Single

/**
 * Represents application dataSource. Implementation differs regarding mock or remote flavour
 */

interface IDataSource {
    fun getUserDetails(userId:String): Single<BaseResponse<UserApiEntity>>

}
