package be.kuleuven.howlongtobeat.google

import android.graphics.Bitmap
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

    // TODO encrypt and store externally: https://cloud.google.com/docs/authentication/api-keys?hl=en&visit_id=637642790375688006-1838986332&rd=1
    private val vision = Vision.Builder(NetHttpTransport(), GsonFactory.getDefaultInstance(), null)
        .setVisionRequestInitializer(VisionRequestInitializer("AIzaSyCaMjQQOY7508y95riDhr25fsrqe3m2JW0"))
        .setApplicationName("How Long To Beat")
        .build()

    private suspend fun findCartCodeViaGoogleVision(cameraSnap: Bitmap): String? {
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
        return gbId?.description ?: null
    }

    override suspend fun recognizeCartCode(image: Bitmap): String? = findCartCodeViaGoogleVision(image)
}
