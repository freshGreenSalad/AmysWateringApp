package com.example.amyswateringapp.wateringAppScreen

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log


fun saveInMediastore(bitmap: Bitmap, context: Context, plant: String): Uri {


    val collection  = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Images.Media.getContentUri(
            MediaStore.VOLUME_EXTERNAL_PRIMARY
        )
    } else {
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
    }

    val photoData = ContentValues().apply{
        put(MediaStore.Images.Media.DISPLAY_NAME, plant)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.WIDTH, bitmap.width)
        put(MediaStore.Images.Media.HEIGHT, bitmap.height)
    }
    return try {

        val contentResolver = context.contentResolver

        val uri = contentResolver.insert(collection, photoData)?.also { uri->
            contentResolver.openOutputStream(uri).use { stream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream)
            }
        }
        uri!!
        
    }catch (e:Exception){
        Log.d("saveToMediaStore", "failed to retreive uri from file write to media store")
        Uri.EMPTY
    }
}