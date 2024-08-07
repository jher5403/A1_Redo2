package com.lab1.a1_redo2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.lab1.a1_redo2.Nav.TaskNavigator
import com.lab1.a1_redo2.ui.theme.A1_Redo2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            A1_Redo2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TaskNavigator()
                }
            }
        }
    }
}
