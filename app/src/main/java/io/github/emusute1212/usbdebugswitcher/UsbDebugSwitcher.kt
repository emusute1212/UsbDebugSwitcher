package io.github.emusute1212.usbdebugswitcher

import android.content.ContentResolver
import android.provider.Settings

object UsbDebugSwitcher {
    fun getCurrentValue(contentResolver: ContentResolver): UsbDebug {
        return Settings.Global.getInt(contentResolver, Settings.Global.ADB_ENABLED).let {
            UsbDebug.from(it)
        }
    }

    fun switch(
        contentResolver: ContentResolver,
        onChangedValue: (UsbDebug) -> Unit = {}
    ) {
        val nextUsbDebug = getCurrentValue(contentResolver).nextValue
        Settings.Global.putInt(
            contentResolver,
            Settings.Global.ADB_ENABLED,
            nextUsbDebug.settingsValue
        )
        onChangedValue(nextUsbDebug)

    }

    sealed interface UsbDebug {
        val settingsValue: Int
        val nextValue: UsbDebug

        data object Enabled : UsbDebug {
            override val settingsValue = 1
            override val nextValue = Disabled
        }

        data object Disabled : UsbDebug {
            override val settingsValue = 0
            override val nextValue = Enabled
        }

        companion object {
            fun from(settingsValue: Int): UsbDebug {
                return when (settingsValue) {
                    Enabled.settingsValue -> Enabled
                    Disabled.settingsValue -> Disabled
                    else -> throw IllegalArgumentException("settingsValue must be 0 or 1")
                }
            }
        }
    }
}