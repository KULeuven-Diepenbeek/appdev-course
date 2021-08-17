package be.kuleuven.howlongtobeat

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
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

class LoadingFragment : Fragment(R.layout.fragment_loading) {

    private lateinit var hltbClient: HLTBClient
    private lateinit var cartRepo: CartridgesRepository
    private lateinit var visionClient: GoogleVisionClient

    private lateinit var cameraPermissionActivityResult: ActivityResultLauncher<String>
    private lateinit var cameraActivityResult: ActivityResultLauncher<Void>
    private lateinit var main: MainActivity
    private lateinit var binding: FragmentLoadingBinding

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

        cameraActivityResult = registerForActivityResult(ActivityResultContracts.TakePicturePreview(), this::cameraSnapTaken)
        cameraPermissionActivityResult = registerForActivityResult(ActivityResultContracts.RequestPermission(), this::cameraPermissionAcceptedOrDenied)
        tryToMakeCameraSnap()

        return binding.root
    }

    private fun cameraSnapTaken(pic: Bitmap) {
        MainScope().launch{
            findGameBasedOnCameraSnap(pic)
        }
    }

    private suspend fun findGameBasedOnCameraSnap(pic: Bitmap) {
        progress("Unleashing Google Vision on the pic...")
        // TODO remove in future
        val dummypic = BitmapFactory.decodeResource(resources, R.drawable.supermarioland2)
        val cartCode = visionClient.findCartCodeViaGoogleVision(dummypic)

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
            progress("Camera permission required!")
        }
    }

    private fun tryToMakeCameraSnap() {
        progress("Making snapshot with camera...")
        if(ContextCompat.checkSelfPermission(main, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            cameraPermissionActivityResult.launch(Manifest.permission.CAMERA)
        }
        makeCameraSnap()
    }

    private fun makeCameraSnap() {
        cameraActivityResult.launch(null)
    }

    private fun progress(msg: String) {
        if(!binding.indeterminateBar.isAnimating) {
            binding.indeterminateBar.animate()
        }
        binding.txtLoading.text = msg
    }

    private fun errorInProgress(msg: String) {
        progress(msg)
        binding.indeterminateBar.clearAnimation()
        Snackbar.make(requireView(), msg, Snackbar.LENGTH_LONG).show()
        // todo show retry button or something?
    }


}