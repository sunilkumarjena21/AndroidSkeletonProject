package com.androidapp.skeletonproject.errors

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil.inflate
import com.androidapp.skeletonproject.R
import com.androidapp.skeletonproject.databinding.CustomSnackbarBinding
import com.google.android.material.snackbar.Snackbar



fun manageError(error: Throwable, context: Activity) {

    val title = R.string.error
    var message = context.resources.getString(R.string.error)

    error.message?.let {
        message = it
    }
    val view: View = context.findViewById(android.R.id.content)
    val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)

    //Retrieve the layout of the Snack Bar
    val snackLayout: Snackbar.SnackbarLayout = snackBar.view as Snackbar.SnackbarLayout

    // remove default text of the Snackbar
    val textView: TextView = snackLayout.findViewById (com.google.android.material.R.id.snackbar_text)
    textView.visibility = View.INVISIBLE

    // Inflate custom snack bar layout
    val  binding : CustomSnackbarBinding = inflate(LayoutInflater.from(context), R.layout.custom_snackbar, null, false)

    snackLayout.addView(binding.root, 0)

    //Manage background
    snackLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.snackBackground))

    //Manage title
    binding.title.setText(title)
    binding.title.setTextColor(ContextCompat.getColor(context, R.color.warning))

    //gestion sous titre
    binding.subtitle.text = message

    snackBar.show()
}

