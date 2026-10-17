package com.example.android_simplevideoeditor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.android_simplevideoeditor.ui.theme.AndroidSimpleVideoEditorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val videoMetadata: List<MediaItem> = ListOf<MediaItem>()

        videoMetadata.add(
            id = 1L,
            uri = "https://i.pinimg.com/736x/56/e2/bc/56e2bc5fe72a1bed36a845cea55cf39f.jpg"

        )

        videoMetadata.add(
            id = 2L,
            uri = "https://i.pinimg.com/736x/b1/51/a7/b151a719706260e7c3e3bd4b64ab3cb1.jpg"
        )

        videoMetadata.add(
            id = 3L,
            uri = "https://i.pinimg.com/736x/54/95/69/5495699563b61bad56f5f1d2eaa26c6e.jpg"
        )
        enableEdgeToEdge()
        setContent {
            AndroidSimpleVideoEditorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ThumbnailGrid(videoMetadata)
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
        items(videoMetadata){ //function to test the grid
                AsyncImage(
                    modifier = Modifier.aspectRatio(1f),
                    model = videoMetadata.uri,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    val videoMetadata: List<MediaItem> = ListOf<MediaItem>()

    videoMetadata.add(
        id = 1L,
        uri = "https://i.pinimg.com/736x/56/e2/bc/56e2bc5fe72a1bed36a845cea55cf39f.jpg"

    )

    videoMetadata.add(
        id = 2L,
        uri = "https://i.pinimg.com/736x/b1/51/a7/b151a719706260e7c3e3bd4b64ab3cb1.jpg"
    )

    videoMetadata.add(
        id = 3L,
        uri = "https://i.pinimg.com/736x/54/95/69/5495699563b61bad56f5f1d2eaa26c6e.jpg"
    )

    AndroidSimpleVideoEditorTheme {
        ThumbnailGrid(videoMetadata)
    }
}