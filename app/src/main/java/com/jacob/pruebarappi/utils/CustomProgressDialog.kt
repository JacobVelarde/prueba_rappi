package com.jacob.pruebarappi.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import com.jacob.pruebarappi.R

class CustomProgressDialog(context: Context): Dialog(context) {

    private val mContext : Context

    init {
        this.mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window?.requestFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        this.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val view = (mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
            R.layout.view_progress_dialog, null)

        setContentView(view)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }
}