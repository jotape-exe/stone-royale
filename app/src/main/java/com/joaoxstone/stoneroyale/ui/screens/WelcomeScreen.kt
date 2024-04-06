package com.joaoxstone.stoneroyale.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joaoxstone.stoneroyale.R
import com.joaoxstone.stoneroyale.ui.theme.StoneRoyaleTheme

class WelcomeScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StoneRoyaleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyCompose()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyCompose() {
    val strokeBorder = BorderStroke(
        0.5.dp, Brush.horizontalGradient(
            listOf(MaterialTheme.colorScheme.onPrimary, Color.Transparent)
        )
    )
    val strokeBorderTroph = BorderStroke(
        0.5.dp, Brush.horizontalGradient(
            listOf( Color.Transparent, MaterialTheme.colorScheme.onPrimary)
        )
    )
    val brush = Brush.horizontalGradient(listOf(Color(0XFF1650b5), Color(0XFF1f6af2)))
    var input by rememberSaveable { mutableStateOf("") }
    StoneRoyaleTheme {
        Box(modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(brush = brush)
                inset(10f) {
                    drawRect(brush = brush)
                    inset(5f) {
                        drawRect(brush = brush)
                    }
                }
            }) {
            Surface(modifier = Modifier.padding(8.dp), color = Color.Transparent) {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    IconButton(
                        onClick = { /*TODO*/ }, modifier = Modifier.border(
                            strokeBorder,
                            shape = MaterialTheme.shapes.medium
                        )
                    ) {
                        Image(
                            modifier = Modifier.padding(4.dp),
                            painter = painterResource(id = R.drawable.cardicon),
                            contentDescription = null,
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }, modifier = Modifier.border(
                        strokeBorder,
                        shape = MaterialTheme.shapes.medium
                    )) {
                        Image(
                            modifier = Modifier.padding(4.dp),
                            painter = painterResource(id = R.drawable.clanicon),
                            contentDescription = null,
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }, modifier = Modifier.border(
                        strokeBorder,
                        shape = MaterialTheme.shapes.medium
                    )) {
                        Image(
                            modifier = Modifier.padding(4.dp),
                            painter = painterResource(id = R.drawable.player),
                            contentDescription = null,
                        )
                    }
                }
            }
            Image(
                painter = painterResource(id = R.drawable.king),
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .size(230.dp)
            )

            Surface(color = Color.Transparent,modifier = Modifier
                .padding(8.dp)
                .align(Alignment.TopEnd)
                .border(
                    strokeBorderTroph,
                    shape = MaterialTheme.shapes.medium
                )) {
                IconButton(onClick = { /*TODO*/ }) {
                    Image(
                        modifier = Modifier.padding(4.dp),
                        painter = painterResource(id = R.drawable.github),
                        contentDescription = null,
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxHeight(.769f)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = input,
                        label = { Text(text = "Player TAG") },
                        onValueChange = {
                            input = it
                        }, modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium,
                        prefix = { Text(text = "#") },
                        supportingText = { Text(text = "Exemplo: #890UYLVV") }
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.fillMaxWidth(),
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(text = "Buscar", modifier = Modifier.padding(end = 8.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.magnifying_glass),
                            contentDescription = ""
                        )
                    }
                }
            }
        }

    }
}

