package be.kuleuven.howlongtobeat

import android.graphics.Bitmap
import com.google.api.services.vision.v1.model.Image
import java.io.ByteArrayOutputStream


fun Bitmap.asEncodedGoogleVisionImage(): Image {
    val stream = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.JPEG, 100, stream)
    return Image().apply {
        encodeContent(stream.toByteArray())
    }
}

