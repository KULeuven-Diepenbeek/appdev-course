package be.kuleuven.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import be.kuleuven.login.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnLogin.setText("lol")
        binding.btnLogin.setOnClickListener(this::login)
    }

    private fun login(view: View) {
        if(binding.txtLogin.text.toString().isEmpty()) {
            msg("Your username is empty!", view)
            return
        }
        if(!binding.txtPassword.text.contentEquals("supersecret")) {
            msg("Invalid password!", view)
            return
        }

        msg("And we're in!", view)
    }

    private fun msg(text: String, view: View) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()

    }
}