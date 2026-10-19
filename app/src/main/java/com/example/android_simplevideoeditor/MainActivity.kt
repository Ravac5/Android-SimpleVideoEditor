package com.example.android_simplevideoeditor

import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.media3.common.MediaItem as ExoMediaItem
import coil3.compose.AsyncImage
import com.example.android_simplevideoeditor.ui.theme.AndroidSimpleVideoEditorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            AndroidSimpleVideoEditorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "gallery",
                        modifier = Modifier.padding(innerPadding)

                    ) {
                        composable("gallery") {
                            GalleryScreen(onVideoClick = {video -> navController.navigate("viewer/${video.id}")})
                        }
                        composable("viewer/{id}", arguments = listOf(navArgument("id") { type = NavType.LongType })){ backStackEntry ->
                            val id = backStackEntry.arguments?.getLong("id")?: return@composable
                            val uri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id)
                            VideoPlayer(uri = uri, modifier = Modifier.fillMaxSize())
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ThumbnailGrid(videoMetadata: List<MediaItem>, modifier: Modifier = Modifier, onVideoClick: (MediaItem) -> Unit) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(count = 3),
        verticalArrangement = Arrangement.spacedBy(2.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        items(items = videoMetadata, key = {it.id}){
            Box(Modifier.clickable{onVideoClick(it)}) {
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

@Composable
fun GalleryScreen(modifier: Modifier = Modifier, onVideoClick: (MediaItem) -> Unit) {
    val context = LocalContext.current
    var videos by remember { mutableStateOf<List<MediaItem>>(emptyList())}
    val alreadyGranted = requiredMediaPermissions().all { permission ->
        ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }
    val hasPermissions = remember { mutableStateOf(alreadyGranted) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestMultiplePermissions()) {
        Log.d("Galeria", "Permisos concedidos: $it")
        hasPermissions.value = it.values.all { results -> results }
    }
    Box(modifier = modifier.fillMaxSize()) {
        if (hasPermissions.value) {
            LaunchedEffect(hasPermissions.value) {
                videos = MediaRepository(context.contentResolver).getVideos()
                Log.d("Galeria", "Videos: ${videos.size}")
            }
            ThumbnailGrid(videos, modifier = Modifier.fillMaxSize(), onVideoClick = onVideoClick)
        } else {
            Button(onClick = { launcher.launch(requiredMediaPermissions())}, modifier = Modifier.align(Alignment.Center)) {
                Text("Dar permisos")
            }
        }
    }
}

@Composable
fun VideoPlayer(uri: Uri, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val player = remember { ExoPlayer.Builder(context).build() }
    LaunchedEffect(uri) {
        player.setMediaItem(ExoMediaItem.fromUri(uri))
        player.prepare()
        player.playWhenReady = true
    }
    DisposableEffect(player) { onDispose { player.release() }}
    AndroidView(modifier = modifier, factory = {PlayerView(it).apply { this.player = player }})
}

/*@Preview(showBackground = true)
@Composable
fun Preview() {
    val data1 = MediaItem(1L,"https://i.pinimg.com/736x/56/e2/bc/56e2bc5fe72a1bed36a845cea55cf39f.jpg".toUri(),"idk",1000,1000,1000,)
    val data2 = MediaItem(id = 2L, uri = "https://i.pinimg.com/736x/b1/51/a7/b151a719706260e7c3e3bd4b64ab3cb1.jpg".toUri(), name = "idk2", duration = 1000, size = 1000, dateAdded = 1000)
    val data3 = MediaItem(id = 3L, uri = "https://i.pinimg.com/736x/54/95/69/5495699563b61bad56f5f1d2eaa26c6e.jpg".toUri(), name = "idk3", duration = 1000, size = 1000, dateAdded = 1000)
    val videoMetadata: List<MediaItem> = listOf<MediaItem>(data1,data2,data3)
    AndroidSimpleVideoEditorTheme {
        ThumbnailGrid(videoMetadata
    }
}*/