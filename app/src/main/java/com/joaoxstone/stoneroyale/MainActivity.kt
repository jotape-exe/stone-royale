package com.joaoxstone.stoneroyale

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joaoxstone.stoneroyale.data.model.player.PlayerResponse
import com.joaoxstone.stoneroyale.data.repository.PlayerRepository
import com.joaoxstone.stoneroyale.data.repository.remote.RetrofitClient
import com.joaoxstone.stoneroyale.data.repository.remote.network.ClashRoyaleService
import com.joaoxstone.stoneroyale.ui.components.PlayerSimpleCard
import com.joaoxstone.stoneroyale.ui.screens.WelcomeScreen
import com.joaoxstone.stoneroyale.ui.theme.StoneRoyaleTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


val api = PlayerRepository()

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoneRoyaleTheme {
                var loading by remember { mutableStateOf(false) }
                var player by remember {
                    mutableStateOf(PlayerResponse())
                }


                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Button(
                            onClick = {
                                startActivity(
                                    Intent(
                                        applicationContext,
                                        WelcomeScreen::class.java
                                    )
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Text(text = "Welcome")
                        }
                        Button(onClick = {
                            runBlocking {
                                player = callApi()
                            }
                        }) {
                            Text(text = "Call API")
                            Icon(Icons.Sharp.KeyboardArrowRight, contentDescription = "simbol")
                        }

                        if (loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.width(64.dp),
                                color = MaterialTheme.colorScheme.primary,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                            )
                        }
                        AnimatedVisibility(player.tag != null) {
                            PlayerSimpleCard(
                                playerName = player.name!!,
                                playerTag = player.tag!!,
                                arenaId = player.arena?.id!!
                            )
                        }
                    }

                }
            }
        }
    }
}

suspend fun callApi(): PlayerResponse {
    var response: PlayerResponse = PlayerResponse()
    coroutineScope {
        launch {
            try {
                response = api.getPlayer("#89G0UYLVV")
                Log.d("API_RESPONSE", " getPlayer successful: $response")
            } catch (e: Exception) {
                Log.e("API_ERROR", "getPlayer failed: $e")
            } finally {
                //endProcess()
            }
        }
    }

    return response

}
