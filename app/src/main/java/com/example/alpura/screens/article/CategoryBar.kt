package com.example.alpura.screens.article

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoryBar(categories: List<String>, selected: String, onSelect: (String) -> Unit) {
    Text(modifier = Modifier.padding(8.dp), text = "Kategoriler", fontWeight = FontWeight.ExtraBold, fontSize = 32.sp)
    LazyRow(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        items(categories) { category ->
            Button(
                onClick = { onSelect(category) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selected == category) Color.Black else Color.LightGray.copy(alpha = 0.5f)
                ),
                modifier = Modifier.padding(end = 8.dp),
            ) {
                Text(category, color = if (selected == category) Color.White else Color.Black)
            }
        }
    }
}