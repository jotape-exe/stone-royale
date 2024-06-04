package com.joaoxstone.stoneroyale.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.joaoxstone.stoneroyale.R

@Composable
fun SearchContainer(
    modifier: Modifier = Modifier,
    onSearch: (term: String) -> Unit,
    isLoading: Boolean = false,
    color: Color = MaterialTheme.colorScheme.primary,
    supportingText: String,
    label: String
) {
    var input by rememberSaveable { mutableStateOf("") }
    Column(modifier = modifier) {
        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = color,
            ),
            value = input,
            label = { Text(text = label) },
            onValueChange = {
                input = it
            }, modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            prefix = { Text(text = "#") },
            supportingText = { Text(text = supportingText) }
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Card {
            Surface(
                color = color,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = { onSearch(input) }
                    ),
            ) {
                Row(
                    modifier = modifier.padding(1.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(22.dp)
                        )
                    } else {
                        Text(
                            text = "Buscar",
                            modifier = Modifier.padding(end = 8.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.magnifying_glass),
                            contentDescription = "Search icon",
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }

            }
        }


    }
}