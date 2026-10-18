package com.example.android_simplevideoeditor

import android.os.Build
import android.Manifest

fun requiredMediaPermissions(): Array<String> {
   return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
       arrayOf(Manifest.permission.READ_MEDIA_VIDEO)
    } else {
       arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}
