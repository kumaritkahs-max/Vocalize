package com.yourapp.vocalize

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import com.yourapp.vocalize.nav.VocalizeNavHost
import com.yourapp.vocalize.ui.theme.VocalizeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VocalizeTheme {
                Surface {
                    VocalizeNavHost()
                }
            }
        }
    }
}
