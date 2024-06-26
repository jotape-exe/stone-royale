package com.joaoxstone.stoneroyale.app.components.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
    input: String,
    onValueChange: (value: String) -> Unit,
    isError: Boolean = false,
    label: String
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = color,
            ),
            value = input,
            label = { Text(text = label) },
            onValueChange = {
                onValueChange(it)
            }, modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.medium,
            prefix = { Text(text = "#") },
            supportingText = { Text(text = supportingText) },
            isError = isError
        )
        Spacer(modifier = Modifier.padding(8.dp))
        Card {
            Surface(
                color = color,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        onClick = { onSearch(input) },
                        enabled = !isLoading
                    ),
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
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