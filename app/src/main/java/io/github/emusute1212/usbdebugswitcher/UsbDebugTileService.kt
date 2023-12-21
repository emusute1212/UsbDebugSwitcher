package io.github.emusute1212.usbdebugswitcher

import android.content.Context
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

class UsbDebugTileService : TileService() {
    override fun onStartListening() {
        changeTileState(this)
        super.onStartListening()
    }

    override fun onClick() {
        UsbDebugSwitcher.switch(this)
        changeTileState(this)
    }

    private fun changeTileState(context: Context) {
        qsTile?.apply {
            state = when {
                UsbDebugSwitcher.isSwitchEnabled(context) -> when (UsbDebugSwitcher.getCurrentValue(
                    context
                )) {
                    UsbDebugSwitcher.UsbDebug.Enabled -> Tile.STATE_ACTIVE
                    UsbDebugSwitcher.UsbDebug.Disabled -> Tile.STATE_INACTIVE
                }

                else -> Tile.STATE_UNAVAILABLE
            }
            updateTile()
        }
    }
}