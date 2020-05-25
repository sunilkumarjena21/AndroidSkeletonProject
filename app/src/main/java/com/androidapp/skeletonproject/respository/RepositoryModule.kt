package com.androidapp.skeletonproject.respository

import com.androidapp.skeletonproject.respository.AppUserRepository
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Koin Module for the repositories
 */
val repositoryModule: Module = module {
    single { AppUserRepository(get(), get()) }
}
