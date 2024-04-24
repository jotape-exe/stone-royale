package com.joaoxstone.stoneroyale.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import com.joaoxstone.stoneroyale.data.constants.ClashConstants

@Composable
fun PlayerSimpleCard(playerName: String, playerTag: String, arenaId: Int, UCtrophies: Int, modifier: Modifier = Modifier){
    Surface(
        shadowElevation = 8.dp,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(modifier = modifier.fillMaxWidth().clickable {

        }.padding(15.dp)) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Row {
                        Image(modifier = modifier.size(42.dp),painter = painterResource(ClashConstants.getIconArena(arenaId, UCtrophies > 0)!!), contentDescription = "arena")
                       /* Column {
                            Row {
                                Image(modifier = modifier.size(22.dp),painter = painterResource(R.drawable.arena24), contentDescription = "arena")
                            }
                        }*/
                    }
                    Text(
                        text = playerName,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Column {
                    Text(text = playerTag)
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewPlayerSimpleCard(){
    PlayerSimpleCard("João Pedro", "#89GOUYLVV",54000020, 1900)
}