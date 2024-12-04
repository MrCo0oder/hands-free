package com.codebook.handsfree.ui

import android.app.Dialog
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.NumberPicker
import com.codebook.handsfree.R
import com.codebook.handsfree.databinding.ControllerDialogBinding

object ControllerDialog {
    private var isOpened = true
    fun build(
        context: Context,
        onSetValue: ((value: Float) -> Unit)? = null,
        onCancel: (() -> Unit)? = null
    ): Dialog {
        val binding = ControllerDialogBinding.inflate(LayoutInflater.from(context), null, false)
        val dialog = CustomDialog(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            dialog.window?.insetsController?.let {
                it.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
        dialog.setOnShowListener {
            setupPickers(binding) { _, _, _ ->
                if (onSetValue != null) {
                    onSetValue(getPickerValue(binding))
                }
            }
        }
        binding.buttonNegative.setOnClickListener {
            onCancel?.invoke()
            dialog.dismiss()
        }
        binding.collapseButton.setOnClickListener {
            if (isOpened){
                binding.constraintLayout.visibility = View.GONE
                binding.collapseButton.setImageResource(R.drawable.ic_arrow_up)

                isOpened = false
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    dialog.window?.insetsController?.hide(WindowInsets.Type.systemBars())
                }
            }else{
                binding.constraintLayout.visibility = View.VISIBLE
                binding.collapseButton.setImageResource(R.drawable.ic_arrow)
                isOpened = true
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    dialog.window?.insetsController?.show(WindowInsets.Type.systemBars())
                }
            }
        }

        return dialog
    }

    private fun setupPickers(
        binding: ControllerDialogBinding,
        onValueChangedListener: NumberPicker.OnValueChangeListener
    ) {
        binding.brightnessValuePicker.apply {
            maxValue = 10
            minValue = 0
            value = 0
            setOnValueChangedListener(onValueChangedListener)
        }
    }

    private fun getPickerValue(binding: ControllerDialogBinding): Float {
        return (binding.brightnessValuePicker.value / 10f)
    }
}