package com.androidapp.skeletonproject.screens

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.androidapp.skeletonproject.R
import com.androidapp.skeletonproject.screens.activity.SettingsActivity
import com.androidapp.skeletonproject.utils.getLocalization
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class LoginScreen : AppCompatActivity() {
    private var languageCode: String? = null
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setLanguage()
        signButton.setOnClickListener {
            startActivity(Intent(this@LoginScreen, ProfileActivity::class.java))
        }
    }


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private fun setLanguage() {
        languageCode = getLocalization(this)
        if (languageCode != null) {
            val split =
                languageCode?.split("-".toRegex())?.dropLastWhile { it.isEmpty() }?.toTypedArray()
                    ?: arrayOf("en", "EN")

            val res = resources
            // Change locale settings in the app.
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.setLocale(Locale(split[0], split[1])) // API 17+ only.
            // Use conf.locale = new Locale(...) if targeting lower versions
            res.updateConfiguration(conf, dm)
        }
    }
    override fun onResume() {
        super.onResume()
        val previousLanguage = languageCode!!
        languageCode = getLocalization(this)
        if (languageCode != null) {
            if (languageCode != previousLanguage) {
                finish()
                startActivity(intent)
            }
        }
    }
}