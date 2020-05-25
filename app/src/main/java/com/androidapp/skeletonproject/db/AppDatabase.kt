package com.androidapp.skeletonproject.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androidapp.skeletonproject.dao.UserDao
import com.androidapp.skeletonproject.model.User


@Database(
    entities = [User::class
        ], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
