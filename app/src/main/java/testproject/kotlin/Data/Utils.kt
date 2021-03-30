package testproject.kotlin.Data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Utils {

    companion object {
        fun ChangeDateFormet(str: String): String {
            val inFormat: DateFormat = SimpleDateFormat("MM/dd/yy", Locale.getDefault())
            val outFormat: DateFormat = SimpleDateFormat("yyyy:mm:dd hh:mm:ss", Locale.getDefault())

            val date = inFormat.parse(str)!!
            Log.e("TIMEEEE: ", outFormat.format(date))
            return outFormat.format(date)
        }

        fun getScaledBitmap(bitmap: Bitmap): Bitmap {
            val maxWidth = 720
            val imageWidth = bitmap.width
            val imageHeight = bitmap.height
            return if (imageWidth > maxWidth) {
                val newHeight = imageHeight * maxWidth / imageWidth
                Bitmap.createScaledBitmap(bitmap, maxWidth, newHeight, false)
            } else {
                bitmap
            }
        }

        fun uriToFile(context: Context, uri: Uri) : File {
            val fileName = uri.path!!.split(":")[1]
            val inputStream = context.contentResolver.openInputStream(uri)
            val bitmap = getScaledBitmap(BitmapFactory.decodeStream(inputStream))
            inputStream?.close()
            val file = File(context.cacheDir.toString() + File.separator + fileName + ".jpg")
            val os: OutputStream = BufferedOutputStream(FileOutputStream(file))
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, os)
            os.close()
            return file
        }
    }
}