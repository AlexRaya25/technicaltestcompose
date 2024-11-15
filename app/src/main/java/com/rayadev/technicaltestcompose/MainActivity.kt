package com.rayadev.technicaltestcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.rayadev.presentation.ui.screens.UserScreen
import com.rayadev.technicaltestcompose.navigation.AppNavigation
import com.rayadev.technicaltestcompose.ui.theme.TechnicaltestcomposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TechnicaltestcomposeTheme {
                AppNavigation()
            }
        }
    }
}