package com.weather.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.weather.R

class Loader {

    companion object {
        private var alertDialog: Dialog? = null

        fun showLoading(context: Context) {

            alertDialog = Dialog(context)
            alertDialog?.setContentView(R.layout.dialog_loader)
            alertDialog?.setCanceledOnTouchOutside(false)
            alertDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            alertDialog?.show()
        }

        fun stopLoading() {
            alertDialog?.dismiss()
        }
    }
}