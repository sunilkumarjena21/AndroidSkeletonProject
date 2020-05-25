package com.androidapp.skeletonproject

import com.androidapp.skeletonproject.services.IDataSource
import org.koin.core.module.Module
import org.koin.dsl.module

/**
 * Koin module for DataSource service
 */
val dataSourceModule : Module = module {
   single { MockDataSource(get()) as IDataSource }
}
