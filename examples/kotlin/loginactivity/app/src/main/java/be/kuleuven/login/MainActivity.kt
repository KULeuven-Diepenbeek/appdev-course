package be.kuleuven.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import be.kuleuven.login.databinding.ActivityMainBinding
import be.kuleuven.login.model.User
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
        val intent = Intent(this, WelcomeActivity::class.java)
        // 1. the "easy but stupid" way
        // intent.putExtra("username", binding.txtUsername.text.toString())
        // 2. the "better" way, using a model
        intent.putExtra("user", createUser())
        startActivity(intent)
    }

    private fun createUser(): User {
        // push all layout values into your model here
        return User(binding.txtUsername.text.toString())
    }

    private fun msg(text: String, view: View) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()

    }
}