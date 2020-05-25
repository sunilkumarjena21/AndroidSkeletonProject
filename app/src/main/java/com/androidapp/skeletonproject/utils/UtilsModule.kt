package com.androidapp.skeletonproject.utils

import org.koin.core.module.Module
import org.koin.dsl.module


val utilsModule : Module = module{
    single {LanguageSetting(get())}
}
