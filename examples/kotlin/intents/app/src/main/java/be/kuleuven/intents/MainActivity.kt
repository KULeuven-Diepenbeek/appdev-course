package be.kuleuven.intents

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import be.kuleuven.intents.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var pictureActivityResult: ActivityResultLauncher<Void>
    private lateinit var binding: ActivityMainBinding
    private val REQ_CALL_ID = 1
    private val REQ_PIC_ID = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // note that registerForActivityResult should be called before the view is in state STARTED
        pictureActivityResult = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bm: Bitmap ->
            msg("bitmap is ${bm.height} high", binding.root)
            binding.imgAvatar.setImageBitmap(bm)
        }

        binding.btnCall.setOnClickListener(this::tryToMakeTheCall)
        binding.btnTakePic.setOnClickListener(this::tryToTakeThePicture)
    }

    private fun tryToTakeThePicture(view: View) {
        // note that we do not need the camera permission here: we use an implicit intent to let an external app handle this
        // it's hidden in ActivityResultContracts.TakePicturePreview(): "return new Intent(MediaStore.ACTION_IMAGE_CAPTURE);"

        //if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
         //   ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),REQ_PIC_ID);
        //} else {
            takeThePicture()
        //}
    }

    private fun takeThePicture() {
        pictureActivityResult.launch(null)
    }

    private fun tryToMakeTheCall(view: View) {
        // this gives a SecurityException without adding android.permission.CALL_PHONE to the list of allowed permissions
        // this STILL gives a SecurityPermission if it's only set in the AndroidManifest, because it's something that needs to be explicitly asked for
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE),REQ_CALL_ID);
        } else {
            makeTheCall()
        }
    }

    private fun makeTheCall() {
        val number = Uri.parse("tel:011112233")

        val intent = Intent(Intent.ACTION_CALL, number)
        if(intent.resolveActivity(applicationContext.packageManager) != null) {
            startActivity(intent)
        } else {
            msg("Your device cannot handle making a call", binding.root)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(grantResults.isEmpty() || grantResults.first() != PackageManager.PERMISSION_GRANTED) {
            // Up to the reader: whoops, not granted. Why? Print a message? etc...
            return
        }

        when(requestCode) {
            REQ_CALL_ID -> makeTheCall()
            REQ_PIC_ID -> takeThePicture()
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun msg(text: String, view: View) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()

    }
}