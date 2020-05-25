package com.androidapp.skeletonproject.dao

import androidx.room.*
import com.androidapp.skeletonproject.model.User
import io.reactivex.Observable
import io.reactivex.Single


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM User")
    fun getSyncUser(): Single<User>

    @Query("SELECT * FROM User")
    fun observeUser(): Observable<User>
}
