package com.bytewave.staffbrief.data.db.converters

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream
import java.io.InputStream


object BitmapConverter {
    @TypeConverter
    fun converterBitmapToString(bitmap: Bitmap?): String? {
        if (bitmap == null) {
            return null
        }
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos)
        val byteArray = baos.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }
    @TypeConverter
    fun converterStringToBitmap(encodedString: String?): Bitmap? {
        if (encodedString == null) {
            return null
        }
        return try {
            val encodeByte = Base64.decode(encodedString, Base64.DEFAULT)
            BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
        } catch (e:Exception) {
            e.printStackTrace()
            null
        }
    }
    fun uriToString(context: Context, uri: Uri): String? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            Log.d("Coverter", "bitmap")
            converterBitmapToString(bitmap)

        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}