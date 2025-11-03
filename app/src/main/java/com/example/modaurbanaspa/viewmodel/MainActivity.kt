package com.example.modaurbanaspa.viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.modaurbanaspa.ui.navigation.AppNavGraph
import com.example.modaurbanaspa.ui.theme.ModaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ModaTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    AppNavGraph()
                }
            }
        }
    }
}