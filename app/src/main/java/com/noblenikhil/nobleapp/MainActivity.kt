package com.noblenikhil.nobleapp

import android.R
import android.graphics.fonts.FontStyle
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.noblenikhil.nobleapp.ui.theme.NobleAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NobleAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MyScreen()
                }
            }
        }
    }
    override fun onStart(){
        super.onStart()
        Log.d("MainActivity", "onStart: ")
    }
    override fun onPause(){
        super.onPause()
        Log.d("MainActivity", "onPause: ")
    }
    override fun onResume(){
        super.onResume()
        Log.d("MainActivity", "onResume: ")
    }
    override fun onRestart(){
        super.onRestart()
        Log.d("MainActivity", "onRestart: ")
    }
    override fun onDestroy(){
        super.onDestroy()
        Log.d("MainActivity", "onDestroy: ")
    }
    override fun onStop(){
        super.onStop()
        Log.d("MainActivity", "onStop: ")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(
                color = Color.Cyan
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
        text = "I am $name!",
        fontSize = 44.sp,
            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
        modifier = modifier.background(Color.White)
        )
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NobleAppTheme {
        Greeting("Android")
    }
}