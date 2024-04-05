package com.joaoxstone.stoneroyale.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
    val brush = Brush.horizontalGradient(listOf(Color(0XFF1650b5), Color.Blue))
    var input by rememberSaveable { mutableStateOf("") }
    StoneRoyaleTheme {
        Box(modifier = Modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(brush = brush) // will allocate a shader to occupy the 200 x 200 dp drawing area
                inset(10f) {
                    /* Will allocate a shader to occupy the 180 x 180 dp drawing area as the
                     inset scope reduces the drawing  area by 10 pixels on the left, top, right,
                    bottom sides */
                    drawRect(brush = brush)
                    inset(5f) {
                        /* will allocate a shader to occupy the 170 x 170 dp drawing area as the
                         inset scope reduces the  drawing area by 5 pixels on the left, top,
                         right, bottom sides */
                        drawRect(brush = brush)
                    }
                }
            }) {
            Image(painter = painterResource(id = R.drawable.king), contentDescription = "", modifier = Modifier
                .align(Alignment.TopCenter)
                .size(230.dp))

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
                        supportingText = { Text(text = "Exemplo: #890UYLVV")}
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

