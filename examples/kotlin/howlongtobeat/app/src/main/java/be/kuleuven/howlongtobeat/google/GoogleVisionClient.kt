package be.kuleuven.howlongtobeat.google

import android.graphics.Bitmap
import be.kuleuven.howlongtobeat.BuildConfig
import be.kuleuven.howlongtobeat.ImageRecognizer
import be.kuleuven.howlongtobeat.asEncodedGoogleVisionImage
import be.kuleuven.howlongtobeat.cartridges.Cartridge
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.vision.v1.Vision
import com.google.api.services.vision.v1.VisionRequestInitializer
import com.google.api.services.vision.v1.model.AnnotateImageRequest
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse
import com.google.api.services.vision.v1.model.Feature
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GoogleVisionClient : ImageRecognizer {

    private suspend fun findCartCodeViaGoogleVision(cameraSnap: Bitmap): String? {
        val vision = buildVisionClient()

        var response: BatchAnnotateImagesResponse
        withContext(Dispatchers.IO) {
            val sml2Data = cameraSnap.asEncodedGoogleVisionImage()

            val req = AnnotateImageRequest().apply {
                features = listOf(Feature().apply {
                    type = "TEXT_DETECTION"
                    maxResults = 10
                })
                image = sml2Data
            }

            val batch = BatchAnnotateImagesRequest().apply {
                requests = listOf(req)
            }
            response = vision.images().annotate(batch).execute()
        }
        if(response.responses.isEmpty()
            || response.responses.get(0).textAnnotations == null
            || response.responses.get(0).textAnnotations.isEmpty()) {
            return null
        }

        val gbId = response.responses.get(0).textAnnotations.filter {
            Cartridge.isValid(it.description)
        }.firstOrNull()
        return gbId?.description
    }

    private fun buildVisionClient(): Vision {
        assert(BuildConfig.GOOGLE_VISION_API_KEY.length > 1)

        return Vision.Builder(NetHttpTransport(), GsonFactory.getDefaultInstance(), null)
            .setVisionRequestInitializer(VisionRequestInitializer(BuildConfig.GOOGLE_VISION_API_KEY))
            .setApplicationName("How Long To Beat")
            .build()
    }

    override suspend fun recognizeCartCode(image: Bitmap): String? = findCartCodeViaGoogleVision(image)
}
