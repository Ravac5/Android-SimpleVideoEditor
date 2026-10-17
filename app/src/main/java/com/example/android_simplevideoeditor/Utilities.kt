package com.example.android_simplevideoeditor

import java.util.Locale

fun formatDuration(duration: Long): String {
    val totalSeconds = duration / 1000
    val hours = totalSeconds / 3600
    return if (hours > 0) {
        "%d:%02d:%02d".format(Locale.US, hours, totalSeconds % 3600 / 60, totalSeconds % 60)
    } else {
        "%d:%02d".format(Locale.US, totalSeconds / 60, totalSeconds % 60)
    }
}