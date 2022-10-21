package com.example.ksp_example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ksp_example.annotation.KspExample
import com.example.ksp_example.ui.theme.AppTheme

@KspExample
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Greeting("Android")
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Scaffold {
        Text(
            modifier = Modifier.padding(it),
            text = "Hello $name!",
        )
    }
}

@Preview
@Composable
fun DefaultPreview() {
    AppTheme {
        Greeting("Android")
    }
}