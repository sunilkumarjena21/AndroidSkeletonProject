package com.androidapp.skeletonproject.utils

import android.content.Context


fun getLocalization(context: Context?): String? {
    return context?.let {
        ENTRY_KEY_LOCALIZATION?.let { it1 ->
            getStringFromPrefs(
                it,
                PREFS_KEY_SETTINGS,
                it1
        )
        }
    }
}

fun saveLocalization(
    context: Context?,
    localization: String?
) {
    context?.let {
        if (ENTRY_KEY_LOCALIZATION != null) {
            if (localization != null) {
                saveStringInPrefs(
                    it,
                    PREFS_KEY_SETTINGS,
                    ENTRY_KEY_LOCALIZATION,
                    localization
                )
            }
        }
    }
}
//PRIVATE PREFS ACCESSING METHODS
private fun saveStringInPrefs(
    ctx: Context,
    prefkey: String,
    entrykey: String,
    string: String
) {
    val sharedPreferences =
        ctx.getSharedPreferences(prefkey, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString(entrykey, string)
    editor.apply()
}


private fun getStringFromPrefs(
    ctx: Context,
    prefkey: String,
    entrykey: String
): String? {
    val sharedPreferences =
        ctx.getSharedPreferences(prefkey, Context.MODE_PRIVATE)
    return sharedPreferences.getString(entrykey, null)
}