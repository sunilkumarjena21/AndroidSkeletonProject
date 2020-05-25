package com.androidapp.skeletonproject.viewmodel

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


/**
 * Koin Module for the view models
 */

val viewModelsModule: Module = module {
    viewModel { UserViewModel(get()) }
}
