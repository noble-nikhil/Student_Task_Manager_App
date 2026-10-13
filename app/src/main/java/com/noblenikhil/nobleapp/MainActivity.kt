package com.noblenikhil.nobleapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.noblenikhil.nobleapp.ui.theme.NobleAppTheme

enum class AppScreen { GREETING, LOGIN, TASKS }

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NobleAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = Color.Black
                ) { innerPadding ->
                    var currentScreen by remember { mutableStateOf(AppScreen.GREETING) }
                    var loggedInUser by remember { mutableStateOf("Nikhil") }

                    when (currentScreen) {
                        AppScreen.GREETING -> {
                            Greeting(
                                name = loggedInUser,
                                onSignInClick = { currentScreen = AppScreen.LOGIN },
                                modifier = Modifier.padding(innerPadding)
                            )
                        }
                        AppScreen.LOGIN -> {
                            MyScreen(
                                onLoginSuccess = { username ->
                                    loggedInUser = username
                                    currentScreen = AppScreen.TASKS
                                }
                            )
                        }
                        AppScreen.TASKS -> {
                            TaskScreen(
                                username = loggedInUser,
                                onLogout = { currentScreen = AppScreen.GREETING }
                            )
                        }
                    }
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
fun Greeting(name: String, onSignInClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "\uD83C\uDF93",
            fontSize = 100.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(350.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "STUDENT TASK MANAGER",
            color = Color.LightGray,
            fontSize = 25.sp,
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(350.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "Hello, $name!",
            color = Color.White,
            fontSize = 32.sp,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(350.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Manage your tasks effortlessly",
            color = Color.Gray,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(350.dp)
        )
        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onSignInClick,
            modifier = Modifier.width(200.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.DarkGray
            )
        ) {
            Text(
                text = "Sign in",
                fontSize = 18.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NobleAppTheme {
        Greeting(name = "Student", onSignInClick = {})
    }
}