package com.androidapp.skeletonproject.utils

import android.content.Context
import android.content.res.Resources
import com.androidapp.skeletonproject.R
import com.google.android.gms.common.util.ArrayUtils

class LanguageSetting(ctx: Context) {

    var language: String? = null
    init {

        //set language from prefs or load default
        language = getLocalization(ctx)!!
        if (language == null) {
            val deviceLanguage =
                Resources.getSystem().configuration.locale.toString().replace("_", "-")
            val appLanguages = ctx.resources.getStringArray(R.array.language)

            if (ArrayUtils.contains(appLanguages, deviceLanguage))
                setLanguage(ctx, language = deviceLanguage)
            else {
                setLanguage(
                    ctx,
                    language = ctx.resources.getStringArray(R.array.language)[0]
                )
            }
        }
    }
    fun setLanguage(ctx: Context, language: String) {
        this.language = language
        saveLocalization(ctx, language)
    }
}