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
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import be.kuleuven.howlongtobeat.cartridges.Cartridge
import be.kuleuven.howlongtobeat.cartridges.CartridgesRepository
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
    private lateinit var cartRepo: CartridgesRepository
    private lateinit var visionClient: GoogleVisionClient

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

        cartRepo = CartridgesRepository.fromAsset(main.applicationContext)
        visionClient = GoogleVisionClient()
        hltbClient = HLTBClient(main.applicationContext)

        cameraActivityResult = registerForActivityResult(ActivityResultContracts.TakePicture(), this::cameraSnapTaken)
        cameraPermissionActivityResult = registerForActivityResult(ActivityResultContracts.RequestPermission(), this::cameraPermissionAcceptedOrDenied)
        binding.btnRetryAfterLoading.setOnClickListener {
            tryToMakeCameraSnap()
        }
        tryToMakeCameraSnap()

        return binding.root
    }

    private fun cameraSnapTaken(succeeded: Boolean) {
        if(!succeeded || snapshot == null) {
            errorInProgress("Photo could not be saved, try again?")
            return
        }

        progress("Scaling image for upload...")
        val bitmap = snapshot!!.toBitmap(main).scaleToWidth(1600)

        MainScope().launch{
            findGameBasedOnCameraSnap(bitmap)
        }
    }

    private suspend fun findGameBasedOnCameraSnap(pic: Bitmap) {
        progress("Unleashing Google Vision on the pic...")
        val cartCode = visionClient.findCartCodeViaGoogleVision(pic)

        if (cartCode == null) {
            errorInProgress("Unable to find a code in your pic. Retry?")
            return
        }

        progress("Found cart code $cartCode, looking in DB...")
        val foundCart = cartRepo.find(cartCode)

        if (foundCart == Cartridge.UNKNOWN_CART) {
            errorInProgress("$cartCode is an unknown game cartridge. Retry?")
            return
        }

        progress("Valid cart code $cartCode, looking in HLTB...")
        hltbClient.find(foundCart.title) {
            Snackbar.make(requireView(), "Found ${it.size} game(s) for cart $cartCode", Snackbar.LENGTH_LONG).show()

            // TODO wat als geen hltb results gevonden?
            val bundle = bundleOf(HowLongToBeatResult.RESULT to it, HowLongToBeatResult.CODE to cartCode)
            findNavController().navigate(R.id.action_loadingFragment_to_hltbResultsFragment, bundle)
        }
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
        if(!binding.indeterminateBar.isVisible) {
            binding.indeterminateBar.visibility = View.VISIBLE
        }
        binding.txtLoading.text = msg
    }

    private fun errorInProgress(msg: String) {
        progress(msg)
        binding.indeterminateBar.visibility = View.GONE
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG).show()
        binding.btnRetryAfterLoading.show()
    }

}