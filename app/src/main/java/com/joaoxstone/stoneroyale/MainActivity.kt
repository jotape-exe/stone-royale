package com.joaoxstone.stoneroyale

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.joaoxstone.stoneroyale.data.model.player.PlayerResponse
import com.joaoxstone.stoneroyale.data.repository.PlayerRepository
import com.joaoxstone.stoneroyale.ui.components.CardPlayerBottom
import com.joaoxstone.stoneroyale.ui.components.CardPlayerContent
import com.joaoxstone.stoneroyale.ui.components.CardPlayerHead
import com.joaoxstone.stoneroyale.ui.components.PlayerSimpleCard
import com.joaoxstone.stoneroyale.ui.screens.WelcomeScreen
import com.joaoxstone.stoneroyale.ui.theme.StoneRoyaleTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


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

                var playerTag by remember { mutableStateOf("") }
                var hasPlayer by remember { mutableStateOf(false) }

                val scope = rememberCoroutineScope()


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
                        OutlinedTextField(
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp),
                            value = playerTag,
                            prefix = {
                                Text(text = "#")
                            },
                            onValueChange = {
                                playerTag = it
                            })


                        FilledTonalButton(
                            shape = MaterialTheme.shapes.medium,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 20.dp),
                            onClick = {
                                scope.launch {
                                    loading = true
                                    if (playerTag.contains("#")) {
                                        Toast.makeText(
                                            this@MainActivity,
                                            "Não utilize caracteres especiais",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        return@launch
                                    }
                                    val (response, hasPlayerC) = getPlayer(
                                        "#${playerTag.uppercase().replace("O", "0").trim()}"
                                    )
                                    hasPlayer = hasPlayerC
                                    if (hasPlayer) {
                                        player = response
                                    }
                                    loading = false
                                }
                            }) {
                            Text(text = "Buscar")
                            Icon(
                                painterResource(id = R.drawable.magnifying_glass),
                                contentDescription = "simbol"
                            )
                        }

                        Column {
                            if (loading) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .width(64.dp)
                                        .align(Alignment.CenterHorizontally)
                                        .fillMaxWidth(),
                                    color = MaterialTheme.colorScheme.primary,
                                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                                )
                            }
                            AnimatedVisibility(player.tag != null) {
                                PlayerSimpleCard(
                                    cardHeader = {
                                        CardPlayerHead(
                                            playerName = player.name!!,
                                            playerTag = player.tag!!,
                                            arenaId = player.arena?.id!!,
                                            trophies = player.trophies!!,
                                            UCtrophies = player.currentPathOfLegendSeasonResult?.trophies,
                                            leagueNumber = player.currentPathOfLegendSeasonResult?.leagueNumber
                                        )
                                    },
                                    cardPlayerContent = {
                                        CardPlayerContent(
                                            exp = player.expLevel!!,
                                            clanName = player.clan?.name ?: "Sem Clan",
                                            clanTag = player.clan?.tag ?: "",
                                            clanIconId = player.clan?.badgeId
                                        )
                                    },
                                    cardPlayerBottom = {
                                        CardPlayerBottom()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

suspend fun getPlayer(playerTag: String): Pair<PlayerResponse, Boolean> {
    var response: PlayerResponse = PlayerResponse()
    var hasPlayerC = false
    try {
        delay(450)
        response = api.getPlayer(playerTag)
        hasPlayerC = true
    } catch (error: Exception) {
        Log.d("TEM PLAYER: ", error.message.toString())
    }
    return response to hasPlayerC
}
