package com.joaoxstone.stoneroyale.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.joaoxstone.stoneroyale.R

@Composable
fun SearchContainer(modifier: Modifier = Modifier, onSearch: (term: String) -> Unit) {
    var input by rememberSaveable { mutableStateOf("") }
    Column(modifier = modifier) {
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
            onClick = { onSearch(input) },
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