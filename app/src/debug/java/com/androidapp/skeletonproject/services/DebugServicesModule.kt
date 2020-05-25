package com.androidapp.skeletonproject.services

import androidx.room.Room
import com.androidapp.skeletonproject.db.AppDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

val debugServicesModule : Module = module{
    //Inject debug database instance with no encryption
    single { Room.databaseBuilder(get(), AppDatabase::class.java, "SkeletonDB")
        .fallbackToDestructiveMigration()
        .build() }
}
