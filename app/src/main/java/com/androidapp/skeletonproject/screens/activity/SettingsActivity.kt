package com.androidapp.skeletonproject.screens.activity

import android.content.Intent
import android.content.res.Resources
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.Preference.OnPreferenceChangeListener
import androidx.preference.PreferenceFragmentCompat
import com.androidapp.skeletonproject.R
import com.androidapp.skeletonproject.screens.LoginScreen
import com.androidapp.skeletonproject.utils.getLocalization
import com.androidapp.skeletonproject.utils.saveLocalization
import java.util.*


/**
 * A [PreferenceActivity] that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 *
 *
 * See [
 * Android Design: Settings](http://developer.android.com/design/patterns/settings.html) for design guidelines and the [Settings
 * API Guide](http://developer.android.com/guide/topics/ui/settings.html) for more information on developing a Settings UI.
 */
class SettingsActivity : AppCompatActivity() {

    private var fragmentManager: FragmentManager? = null
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBar()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(android.R.id.content, GeneralPreferenceFragment())?.commitAllowingStateLoss()

        val language_code: String = getLocalization(this@SettingsActivity)!!
        if (language_code != null) {
            val split = language_code.split("-").toTypedArray()
            val res: Resources = getResources()
            // Change locale settings in the app.
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.setLocale(Locale(split[0], split[1])) // API 17+ only.
            // Use conf.locale = new Locale(...) if targeting lower versions
            res.updateConfiguration(conf, dm)
        }
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    class GeneralPreferenceFragment : PreferenceFragmentCompat() {
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            addPreferencesFromResource(R.xml.preferences)
            val language = findPreference("language_preference") as ListPreference
            val logout: Preference = findPreference("logout_setting") as Preference

            logout.onPreferenceClickListener = Preference.OnPreferenceClickListener {
                activity?.finish()
               var intent= Intent(activity,LoginScreen::class.java)
                intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or
                            Intent.FLAG_ACTIVITY_NEW_TASK
                )
                startActivity(intent)
                true
            }
            language.onPreferenceChangeListener =
                OnPreferenceChangeListener { preference, newValue ->
                    val language_code = newValue as String
                    val split = language_code.split("-").toTypedArray()
                    saveLocalization(activity, language_code)
                    var res = activity?.resources
                    // Change locale settings in the app.
                    var dm = res?.displayMetrics
                    var conf = res?.configuration
                    conf?.setLocale(Locale(split[0], split[1])) // API 17+ only.
                    // Use conf.locale = new Locale(...) if targeting lower versions
                    res?.updateConfiguration(conf, dm)
                    // Change locale settings in the app.
                    dm = res?.displayMetrics
                    conf = res?.configuration
                    conf?.setLocale(Locale(split[0], split[1])) // API 17+ only.
                    // Use conf.locale = new Locale(...) if targeting lower versions
                    res?.updateConfiguration(conf, dm)
                    val intent = activity?.intent
                    activity?.finish()
                    startActivity(intent)

                    true
                }

        }
    }


    /**
     * Set up the [android.app.ActionBar], if the API is available.
     */
    private fun setupActionBar() {
        val actionBar: ActionBar? =supportActionBar
        if (actionBar != null) {
            actionBar.displayOptions = actionBar.displayOptions or ActionBar.DISPLAY_SHOW_CUSTOM
            val upArrow = resources.getDrawable(R.drawable.arrow_left_white)
            actionBar.setHomeAsUpIndicator(upArrow)
            actionBar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE)
            actionBar.setTitle(
                Html.fromHtml(
                    "<font color=\"#fafafa\">" + resources.getString(
                        R.string.title_activity_settings
                    ) + "</font>"
                )
            )
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.colorPrimary)))
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }
    }

}