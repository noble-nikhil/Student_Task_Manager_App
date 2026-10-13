package com.noblenikhil.nobleapp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MyScreen(
    onLoginSuccess: (username: String) -> Unit = {}
){
    val context = LocalContext.current

    var username by remember {
        mutableStateOf("")
    }
    var reg_no by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    var errorMessage by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
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
        Spacer(modifier = Modifier.padding(8.dp))

        Text(
            text = "STUDENT TASK MANAGER",
            color = Color.LightGray,
            fontSize = 25.sp,
            fontFamily = FontFamily.Serif,
            fontStyle = FontStyle.Normal,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(350.dp)
        )
        Spacer(modifier = Modifier.padding(32.dp))

        Text(
            text = "Enter your Name",
            color = Color.White,
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.width(350.dp)
        )

        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            modifier = Modifier.width(350.dp),
            value = username,
            onValueChange = {
                username = it
                errorMessage = ""
            },
            label = {
                Text("Username")
            }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = "Registration number",
            color = Color.White,
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.width(350.dp)
        )

        Spacer(modifier = Modifier.padding(8.dp))
        TextField(
            modifier = Modifier.width(350.dp),
            value = reg_no,
            onValueChange = {
                reg_no = it
                errorMessage = ""
            },
            label = {
                Text("AP...........")
            }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = "Password",
            color = Color.White,
            fontSize = 16.sp,
            fontStyle = FontStyle.Italic,
            modifier = Modifier.width(350.dp)
        )

        Spacer(modifier = Modifier.padding(5.dp))
        OutlinedTextField(
            modifier = Modifier.width(350.dp),
            value = password,
            onValueChange = {
                password = it
                errorMessage = ""
            },
            label = {
                Text("Enter your password here")
            },
            visualTransformation = PasswordVisualTransformation(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            )
        )

        if (errorMessage.isNotBlank()) {
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.width(350.dp)
            )
        }

        Spacer(modifier = Modifier.padding(15.dp))
        Button(
            onClick = {
                val trimmedUsername = username.trim()
                val trimmedRegNo = reg_no.trim()

                when {
                    trimmedUsername.isEmpty() || trimmedRegNo.isEmpty() || password.isEmpty() -> {
                        val msg = "All 3 fields are compulsory!"
                        errorMessage = msg
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }
                    !trimmedRegNo.matches(Regex("^AP\\d{11}$")) -> {
                        val msg = "Registration number must start with 'AP' followed by 11 digits!"
                        errorMessage = msg
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        errorMessage = ""
                        Toast.makeText(
                            context,
                            "Your entered details : Username: $trimmedUsername and Reg_no: $trimmedRegNo",
                            Toast.LENGTH_LONG
                        ).show()
                        onLoginSuccess(trimmedUsername)
                    }
                }
            },
            modifier = Modifier.wrapContentSize(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.DarkGray
            )
        ) {
            Text(text = "Submit")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyScreenpreview(){

    MyScreen()
}