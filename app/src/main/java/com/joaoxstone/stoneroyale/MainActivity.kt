package com.joaoxstone.stoneroyale

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joaoxstone.stoneroyale.data.repository.PlayerRepository
import com.joaoxstone.stoneroyale.data.repository.remote.RetrofitClient
import com.joaoxstone.stoneroyale.data.repository.remote.network.ClashRoyaleService
import com.joaoxstone.stoneroyale.ui.screens.WelcomeScreen
import com.joaoxstone.stoneroyale.ui.theme.StoneRoyaleTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking


val api  = PlayerRepository()
class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoneRoyaleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Button(onClick = {
                            startActivity(
                                Intent(
                                    applicationContext,
                                    WelcomeScreen::class.java
                                )
                            )
                        }, modifier = Modifier.fillMaxWidth(), shape = MaterialTheme.shapes.medium) {
                            Text(text = "Welcome")
                        }
                        Button(onClick = {
                            callApi()
                        }){
                            Text(text = "Call API")
                            Icon(Icons.Sharp.KeyboardArrowRight, contentDescription = "simbol")
                        }
                    }
                }
            }
        }
    }
}

fun callApi(){
    runBlocking {
        try {
            val response = api.getPlayer("#89G0UYLVV")
            Log.d("API_RESPONSE", " getPlayer successful: $response") // Display log using Log.d
        } catch (e: Exception) {
            Log.e("API_ERROR", "getPlayer failed: $e") // Display error log using Log.e
        }
    }
}
