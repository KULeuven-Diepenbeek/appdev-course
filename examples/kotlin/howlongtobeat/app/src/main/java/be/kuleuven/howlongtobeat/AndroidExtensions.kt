package be.kuleuven.howlongtobeat

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import java.net.URL
import kotlin.math.roundToInt

fun Fragment.requireContentResolver(): ContentResolver = requireContext().contentResolver

fun Uri.toBitmap(contentResolver: ContentResolver): Bitmap
    = BitmapFactory.decodeStream(contentResolver.openInputStream(this))

fun URL.downloadAsImage(): Bitmap =
    BitmapFactory.decodeStream(this.openConnection().getInputStream())

fun Bitmap.save(location: String, context: Context) {
    context.openFileOutput(location, Context.MODE_PRIVATE).use {
        compress(Bitmap.CompressFormat.JPEG, 85, it)
    }
}

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