package be.kuleuven.howlongtobeat

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import androidx.core.view.isVisible
import java.net.URL
import kotlin.math.roundToInt

fun Uri.toBitmap(activity: Activity): Bitmap
    = BitmapFactory.decodeStream(activity.contentResolver.openInputStream(this))

fun URL.downloadAsImage(): Bitmap =
    BitmapFactory.decodeStream(this.openConnection().getInputStream())

fun Bitmap.scaleToWidth(width: Int): Bitmap {
    val aspectRatio = this.width.toFloat() / this.height.toFloat()
    val height = (width / aspectRatio).roundToInt()

    return Bitmap.createScaledBitmap(this, width, height, false)
}

fun View.ensureVisible() {
    if(!this.isVisible) {
        this.visibility = View.VISIBLE
    }
}