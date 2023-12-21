package io.github.emusute1212.usbdebugswitcher

import android.content.Context
import android.provider.Settings

object UsbDebugSwitcher {
    fun isSwitchEnabled(context: Context): Boolean {
        return Settings.Global.getInt(
            context.contentResolver,
            Settings.Global.DEVELOPMENT_SETTINGS_ENABLED
        ).let {
            it == 1
        }
    }

    fun getCurrentValue(context: Context): UsbDebug {
        return Settings.Global.getInt(context.contentResolver, Settings.Global.ADB_ENABLED).let {
            UsbDebug.from(it)
        }
    }

    fun switch(
        context: Context
    ) {
        Settings.Global.putInt(
            context.contentResolver,
            Settings.Global.ADB_ENABLED,
            getCurrentValue(context).nextValue.settingsValue
        )
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