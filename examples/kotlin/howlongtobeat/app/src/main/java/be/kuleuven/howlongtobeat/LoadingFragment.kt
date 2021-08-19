package be.kuleuven.howlongtobeat

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.core.content.PermissionChecker
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import be.kuleuven.howlongtobeat.cartridges.CartridgeFinderViaDuckDuckGo
import be.kuleuven.howlongtobeat.cartridges.CartridgesRepository
import be.kuleuven.howlongtobeat.cartridges.CartridgesRepositoryGekkioFi
import be.kuleuven.howlongtobeat.cartridges.findFirstCartridgeForRepos
import be.kuleuven.howlongtobeat.databinding.FragmentLoadingBinding
import be.kuleuven.howlongtobeat.google.GoogleVisionClient
import be.kuleuven.howlongtobeat.hltb.HLTBClient
import be.kuleuven.howlongtobeat.hltb.HowLongToBeatResult
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.File

class LoadingFragment : Fragment(R.layout.fragment_loading) {

    private lateinit var hltbClient: HLTBClient
    private lateinit var cartRepos: List<CartridgesRepository>
    private lateinit var imageRecognizer: ImageRecognizer

    private lateinit var cameraPermissionActivityResult: ActivityResultLauncher<String>
    private lateinit var cameraActivityResult: ActivityResultLauncher<Uri>
    private lateinit var main: MainActivity
    private lateinit var binding: FragmentLoadingBinding

    private var snapshot: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoadingBinding.inflate(layoutInflater)
        main = activity as MainActivity

        // If we fail to find info in the first repo, it falls back to the second one: a (scraped) DuckDuckGo search.
        cartRepos = listOf(
            CartridgesRepositoryGekkioFi.fromAsset(main.applicationContext),
            CartridgeFinderViaDuckDuckGo(main.applicationContext)
        )
        imageRecognizer = GoogleVisionClient()
        hltbClient = HLTBClient(main.applicationContext)

        cameraActivityResult = registerForActivityResult(ActivityResultContracts.TakePicture(), this::cameraSnapTaken)
        cameraPermissionActivityResult = registerForActivityResult(ActivityResultContracts.RequestPermission(), this::cameraPermissionAcceptedOrDenied)
        binding.btnRetryAfterLoading.setOnClickListener {
            tryToMakeCameraSnap()
        }

        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        val inProgress = savedInstanceState?.getBoolean("inprogress") ?: false
        if(!inProgress) {
            // Don't do this in onCreateView, things go awry if you rotate the smartphone!
            tryToMakeCameraSnap()
        }

        super.onViewStateRestored(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putBoolean("inprogress", snapshot != null)
        }
        super.onSaveInstanceState(outState)
    }

    private fun cameraSnapTaken(succeeded: Boolean) {
        if(!succeeded || snapshot == null) {
            errorInProgress("Photo could not be saved, try again?")
            return
        }

        progress("Scaling image for upload...")
        val bitmap = snapshot!!.toBitmap(requireContentResolver()).scaleToWidth(1600)

        MainScope().launch{
            try {
                findGameBasedOnCameraSnap(bitmap)
            } catch (errorDuringFind: UnableToFindGameException) {
                errorInProgress("${errorDuringFind.message}\nRetry?")
            }
        }
    }

    private suspend fun findGameBasedOnCameraSnap(pic: Bitmap) {
        var picToAnalyze = pic
        // Uncomment this line if you want to stub out camera pictures
        // picToAnalyze = BitmapFactory.decodeResource(resources, R.drawable.sml2)

        progress("Recognizing game cart from picture...")
        val cartCode = imageRecognizer.recognizeCartCode(picToAnalyze)
            ?: throw UnableToFindGameException("No cart code in your pic found")

        progress("Found cart code $cartCode\nLooking in DBs for matching game...")
        val foundCart = findFirstCartridgeForRepos(cartCode, cartRepos)
            ?: throw UnableToFindGameException("$cartCode is an unknown game cart.")

        progress("Valid cart code: $cartCode\n Looking in HLTB for ${foundCart.title}...")
        val hltbResults = hltbClient.find(foundCart)
            ?: throw UnableToFindGameException("HLTB does not know ${foundCart.title}")

        Snackbar.make(requireView(), "Found ${hltbResults.size} game(s) for cart ${foundCart.code}", Snackbar.LENGTH_LONG).show()
        val bundle = bundleOf(
            HowLongToBeatResult.RESULT to hltbResults,
            HowLongToBeatResult.SNAPSHOT_URI to snapshot.toString()
        )
        findNavController().navigate(R.id.action_loadingFragment_to_hltbResultsFragment, bundle)
    }

    private fun cameraPermissionAcceptedOrDenied(succeeded: Boolean) {
        if(succeeded) {
            makeCameraSnap()
        } else {
            errorInProgress("Camera permission required!")
        }
    }

    private fun tryToMakeCameraSnap() {
        binding.btnRetryAfterLoading.hide()
        progress("Making snapshot with camera...")

        if(PermissionChecker.checkSelfPermission(main, Manifest.permission.CAMERA) != PermissionChecker.PERMISSION_GRANTED) {
            cameraPermissionActivityResult.launch(Manifest.permission.CAMERA)
        } else {
            makeCameraSnap()
        }
    }

    private fun createNewTempCameraFile() {
        // a <Provider/> should be present in the manifest file.
        val tempFile = File.createTempFile("hltbCameraSnap", ".png", main.cacheDir).apply {
            createNewFile()
            deleteOnExit()
        }

        snapshot = FileProvider.getUriForFile(main.applicationContext, "${BuildConfig.APPLICATION_ID}.provider", tempFile)
    }

    private fun makeCameraSnap() {
        createNewTempCameraFile()
        cameraActivityResult.launch(snapshot)
    }

    private fun progress(msg: String) {
        binding.indeterminateBar.ensureVisible()
        binding.txtLoading.text = msg
    }

    private fun errorInProgress(msg: String) {
        snapshot = null
        progress(msg)
        binding.indeterminateBar.visibility = View.GONE
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG).show()
        binding.btnRetryAfterLoading.show()
    }

}