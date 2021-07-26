package be.kuleuven.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import be.kuleuven.login.databinding.ActivityWelcomeBinding
import be.kuleuven.login.model.User

class WelcomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val user: User = intent.getSerializableExtra("user") as User
        binding.txtWelcome.text = "Welcome, ${user.name}"
    }
}