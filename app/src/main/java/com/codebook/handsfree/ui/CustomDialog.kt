package com.codebook.handsfree.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager

class CustomDialog(view: View) : Dialog(view.context) {
    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.clearFlags(Window.FEATURE_NO_TITLE)
        //try to make it full screen
        window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        //try to hide system bars
        window?.setGravity(Gravity.BOTTOM)
        window?.setDimAmount(0.0f)
        setContentView(view)
        setCancelable(false)
    }
}