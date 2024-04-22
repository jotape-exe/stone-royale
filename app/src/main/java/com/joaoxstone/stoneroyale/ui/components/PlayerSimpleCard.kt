package com.joaoxstone.stoneroyale.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joaoxstone.stoneroyale.R

@Composable
fun PlayerSimpleCard(playerName: String, playerTag: String, arenaId: Int, modifier: Modifier = Modifier){
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(modifier = modifier.padding(15.dp)) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Image(modifier = modifier.size(42.dp),painter = painterResource(R.drawable.arena24), contentDescription = "arena")
                    Text(
                        text = playerName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column {
                    Text(text = playerTag)
                    Text(text = arenaId.toString())
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewPlayerSimpleCard(){
    PlayerSimpleCard("João Pedro", "#89GOUYLVV",54000000)
}