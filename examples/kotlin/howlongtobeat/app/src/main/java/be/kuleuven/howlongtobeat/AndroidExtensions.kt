package be.kuleuven.howlongtobeat

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import kotlin.math.roundToInt

fun Uri.toBitmap(activity: Activity): Bitmap {
    return BitmapFactory.decodeStream(activity.contentResolver.openInputStream(this))
}

fun Bitmap.scaleToWidth(width: Int): Bitmap {
    val aspectRatio = this.width.toFloat() / this.height.toFloat()
    val height = (width / aspectRatio).roundToInt()

    return Bitmap.createScaledBitmap(this, width, height, false)
}