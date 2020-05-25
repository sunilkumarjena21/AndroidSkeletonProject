package com.androidapp.skeletonproject.screens

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.androidapp.skeletonproject.R
import com.androidapp.skeletonproject.databinding.ActivityDetailBinding
import com.androidapp.skeletonproject.errors.manageError
import com.androidapp.skeletonproject.screens.activity.SettingsActivity
import com.androidapp.skeletonproject.utils.getLocalization
import com.androidapp.skeletonproject.viewmodel.UserViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class ProfileActivity : AppCompatActivity() {

    private val disposables: CompositeDisposable = CompositeDisposable()
    private val userViewModel: UserViewModel by viewModel()
    private var languageCode: String? = null
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLanguage()
        val user_id = "1"

        val binding = DataBindingUtil.setContentView<ActivityDetailBinding>(
            this,
            R.layout.activity_detail
        )
        binding.viewModel = userViewModel
        userViewModel.user_id.value = user_id
        disposables.add(
            userViewModel.getUserDetail()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    if (response.isLoading()) {
                        if (response.data == null) {
                            isCircleLoader(true)
                            isProgressLoading(false)
                        } else {
                            isCircleLoader(false)
                            isProgressLoading(true)
                        }
                    } else if (response.isSuccess()) {
                        isProgressLoading(false)
                        isCircleLoader(false)
                    } else if (response.isError()) {
                        isProgressLoading(false)
                        isCircleLoader(false)
                        manageError(response.error!!, this)
                    }
                }
        )
        settingButton.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, SettingsActivity::class.java))
        }
        detailAppBar?.setNavigationOnClickListener { onBackPressed() }
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

    /**
     * A progress bar to be displayed when offline data are displayed and remote data are loading
     */
    private fun isProgressLoading(loading: Boolean) {
        loadingBar.visibility = if (loading) View.VISIBLE else View.GONE
    }

    /**
     * A circle loader to be displayed when no offline data are available and remote data are loading
     */
    private fun isCircleLoader(loading: Boolean) {
        circleloader.visibility = if (loading) View.VISIBLE else View.GONE
    }
}
