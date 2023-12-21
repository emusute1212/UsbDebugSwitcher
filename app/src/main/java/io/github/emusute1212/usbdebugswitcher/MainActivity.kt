package io.github.emusute1212.usbdebugswitcher

import android.content.ContentResolver
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import io.github.emusute1212.usbdebugswitcher.ui.theme.UsbDebugSwitcherTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UsbDebugSwitcherTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content() {
    val context = LocalContext.current
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("UsbDebug: ")
            var usbDebugState: UsbDebugSwitcher.UsbDebug by remember {
                mutableStateOf(UsbDebugSwitcher.getCurrentValue(context))
            }
            Switch(
                checked = when (usbDebugState) {
                    UsbDebugSwitcher.UsbDebug.Disabled -> false
                    UsbDebugSwitcher.UsbDebug.Enabled -> true
                },
                onCheckedChange = {
                    UsbDebugSwitcher.switch(context)
                    usbDebugState = UsbDebugSwitcher.getCurrentValue(context)
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewContent() {
    UsbDebugSwitcherTheme {
        Content()
    }
}