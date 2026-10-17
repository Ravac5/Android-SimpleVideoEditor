package com.example.android_simplevideoeditor

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.toString
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import com.example.android_simplevideoeditor.ui.theme.AndroidSimpleVideoEditorTheme
import kotlin.coroutines.coroutineContext

class MainActivity : ComponentActivity() {
    val data1 = MediaItem(1L,"https://i.pinimg.com/736x/56/e2/bc/56e2bc5fe72a1bed36a845cea55cf39f.jpg".toUri(),"idk",1000,1000,1000,)
    val data2 = MediaItem(id = 2L, uri = "https://i.pinimg.com/736x/b1/51/a7/b151a719706260e7c3e3bd4b64ab3cb1.jpg".toUri(), name = "idk2", duration = 1000, size = 1000, dateAdded = 1000)
    val data3 = MediaItem(id = 3L, uri = "https://i.pinimg.com/736x/54/95/69/5495699563b61bad56f5f1d2eaa26c6e.jpg".toUri(), name = "idk3", duration = 1000, size = 1000, dateAdded = 1000)
    val videoMetadata: List<MediaItem> = listOf<MediaItem>(data1,data2,data3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            AndroidSimpleVideoEditorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ThumbnailGrid(videoMetadata, Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ThumbnailGrid(videoMetadata: List<MediaItem>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(count = 3),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(items = videoMetadata, key = {it.id}){ //function to test the grid
            Box() {
                AsyncImage(
                    modifier = Modifier.aspectRatio(1f),
                    model = it.uri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
                Text(text = formatDuration(it.duration),modifier = Modifier.align(Alignment.BottomEnd).background(Color.Black.copy(alpha = 0.6f)), color = Color.White)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val data1 = MediaItem(1L,"https://i.pinimg.com/736x/56/e2/bc/56e2bc5fe72a1bed36a845cea55cf39f.jpg".toUri(),"idk",1000,1000,1000,)
    val data2 = MediaItem(id = 2L, uri = "https://i.pinimg.com/736x/b1/51/a7/b151a719706260e7c3e3bd4b64ab3cb1.jpg".toUri(), name = "idk2", duration = 1000, size = 1000, dateAdded = 1000)
    val data3 = MediaItem(id = 3L, uri = "https://i.pinimg.com/736x/54/95/69/5495699563b61bad56f5f1d2eaa26c6e.jpg".toUri(), name = "idk3", duration = 1000, size = 1000, dateAdded = 1000)
    val videoMetadata: List<MediaItem> = listOf<MediaItem>(data1,data2,data3)
    AndroidSimpleVideoEditorTheme {
        ThumbnailGrid(videoMetadata)
    }
}