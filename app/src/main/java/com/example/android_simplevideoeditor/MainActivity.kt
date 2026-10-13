package com.example.android_simplevideoeditor

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.android_simplevideoeditor.ui.theme.AndroidSimpleVideoEditorTheme

class MainActivity : ComponentActivity() {
    val photos = (0..100).toList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AndroidSimpleVideoEditorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    thumbnailGrid(photos = photos)
                }
            }
        }
    }
}

@Composable
fun thumbnailGrid(photos: List<Int>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(count = 3),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(photos){ //function to test the grid
                AsyncImage(
                    modifier = Modifier.aspectRatio(1f),
                    model = "https://i.pinimg.com/236x/87/91/67/87916753a09bcedd0735dcb868fa6ad8.jpg",
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val photos = (0..100).toList()
    AndroidSimpleVideoEditorTheme {
        thumbnailGrid(photos = photos)
    }
}