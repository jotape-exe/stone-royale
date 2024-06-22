package com.joaoxstone.stoneroyale.app.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.joaoxstone.stoneroyale.R

@Composable
fun EmptyData(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.barb),
            contentDescription = null
        )

        Text(
            modifier = Modifier.width(180.dp),
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            text = buildAnnotatedString {
                append("Digite uma ")
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append("TAG")
                }
                append(" para buscar o perfil.")
            }
        )
    }
}