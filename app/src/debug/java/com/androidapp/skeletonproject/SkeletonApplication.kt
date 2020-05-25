package com.androidapp.skeletonproject
import android.app.Application
import android.content.Context
import com.androidapp.skeletonproject.dao.daoModule
import com.androidapp.skeletonproject.respository.repositoryModule
import com.androidapp.skeletonproject.services.debugServicesModule
import com.androidapp.skeletonproject.services.servicesModule
import com.androidapp.skeletonproject.utils.utilsModule
import com.androidapp.skeletonproject.viewmodel.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

import timber.log.Timber

class SkeletonApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: SkeletonApplication? = null
        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        //Initialize Timber if in debug mode
        Timber.plant(Timber.DebugTree())
        // Start Koin for dependency injection
        startKoin {
            // Use Koin Android Logger
            androidLogger()
            // declare Android context
            androidContext(this@SkeletonApplication)
            // declare modules to use
            modules(listOf(
                dataSourceModule,
                debugServicesModule,
                viewModelsModule,
                servicesModule,
                daoModule,
                repositoryModule,
                utilsModule))
        }
    }
}
