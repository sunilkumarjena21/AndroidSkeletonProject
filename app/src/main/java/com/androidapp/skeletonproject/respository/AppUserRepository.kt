package com.androidapp.skeletonproject.respository

import com.androidapp.skeletonproject.dao.UserDao
import com.androidapp.skeletonproject.model.User
import com.androidapp.skeletonproject.model.apientity.UserApiEntity
import com.androidapp.skeletonproject.model.apientity.toBusinessEntity
import com.androidapp.skeletonproject.network.BaseResponse
import com.androidapp.skeletonproject.network.NetworkBoundSource
import com.androidapp.skeletonproject.services.IDataSource
import com.androidapp.skeletonproject.utils.AppResource
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject


class AppUserRepository(val dataSource: IDataSource, val appUserDao: UserDao) {

    /**
     * Called service name
     */
    val USER_SERVICE = "user_service"

    /**
     * A subject to refresh database with fresh data when triggered
     */
    val refresh: BehaviorSubject<Boolean> = BehaviorSubject.createDefault(true)

    /**
     * Get the user
     */
    fun getUser(userId:String): Observable<AppResource<User>> {

        return Observable.create { emitter ->
            object :
                NetworkBoundSource<User, User, UserApiEntity>(emitter = emitter) {
                override fun getRemote(): Single<BaseResponse<UserApiEntity>> {
                    return dataSource.getUserDetails(userId)
                }

                override fun getLocal(): Single<User> {
                    return appUserDao.getSyncUser()
                }

                override fun saveCallResult(data: User) {
                    return appUserDao.insertUser(data)
                }

                override fun observeDatabase(): Observable<User> {
                    return appUserDao.observeUser()
                }

                override val calledService: String
                    get() = USER_SERVICE

                override val refreshSubject: BehaviorSubject<Boolean>
                    get() = refresh

                override fun convertToBusinessEntity(remoteType: UserApiEntity): User {
                    return remoteType.toBusinessEntity()
                }
            }.startFetchingData()
        }
    }
}
