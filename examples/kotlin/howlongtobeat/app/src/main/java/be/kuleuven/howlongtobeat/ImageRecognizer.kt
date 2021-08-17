package be.kuleuven.howlongtobeat

import android.graphics.Bitmap

interface ImageRecognizer {
    suspend fun recognizeCartCode(image: Bitmap): String?
}