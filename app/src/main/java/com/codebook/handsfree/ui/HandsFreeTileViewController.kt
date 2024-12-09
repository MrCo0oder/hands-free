package com.codebook.handsfree.ui

import android.app.Dialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

class HandsFreeTileViewController : TileService() {

    private val configurationChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == Intent.ACTION_CONFIGURATION_CHANGED) {
                qsTile.updateTile()
            }
        }
    }

    private var controllerDialog: Dialog? = null

    override fun onCreate() {
        super.onCreate()
        registerReceiver(
            configurationChangeReceiver,
            IntentFilter(Intent.ACTION_CONFIGURATION_CHANGED)
        )
    }

    override fun onStartListening() {
        super.onStartListening()
        qsTile.state = Tile.STATE_INACTIVE
        qsTile.updateTile()
    }

    override fun onClick() {
        super.onClick()
        if (showControllerDialog() != null)
            showDialog(showControllerDialog())
    }

    private fun showControllerDialog(): Dialog? {
        if (controllerDialog?.isShowing == true) return null
        controllerDialog = ControllerDialog.build(
            applicationContext,
            onSetValue = { value ->
                controllerDialog?.window?.setDimAmount(value)
            },
            onCancel = {
            }
        )
        return controllerDialog
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(configurationChangeReceiver)
    }
}