package com.joaoxstone.stoneroyale.app.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joaoxstone.stoneroyale.R
import com.joaoxstone.stoneroyale.app.theme.StoneRoyaleTheme


@Composable
fun WelcomeScreen(
    navigationAction: () -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    val url = "https://github.com/jotape-exe/stone-royale.git"
    val gitHubIntent = Intent(Intent.ACTION_VIEW)
    gitHubIntent.setData(Uri.parse(url))

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(contentAlignment = Alignment.Center){
            Column(
                modifier = Modifier.padding(46.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.outline_heheha),
                    contentDescription = null
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    Text(
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                        text = "HI HI HI HA!",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.ExtraBold,
                    )
                    Text(
                        text = buildAnnotatedString {
                            append("Muito legal ter você aqui! Este é o \n")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Stone Royale")
                            }
                            append(" App desenvolvido pelo")
                        },
                        textAlign = TextAlign.Center
                    )
                    AssistChip(
                        modifier = Modifier.padding(8.dp),
                        onClick = {
                            context.startActivity(Intent(gitHubIntent))
                        },
                        shape = MaterialTheme.shapes.medium,
                        label = {
                            Icon(painter = painterResource(id = R.drawable.github), contentDescription = "github")
                            Text(text = "joaoxstone", Modifier.padding(start = 8.dp))
                        }
                    )
                    Text(
                        text = buildAnnotatedString {
                            append("Espero de verdade que tu se divirta usando o app ")
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("^^")
                            }
                        },
                        textAlign = TextAlign.Center
                    )
                }
                Button(
                    onClick = {
                        navigationAction()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(text = "COMEÇAR")
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PVWelcomeScreen() {
    StoneRoyaleTheme {
        WelcomeScreen(navigationAction = { })
    }
}