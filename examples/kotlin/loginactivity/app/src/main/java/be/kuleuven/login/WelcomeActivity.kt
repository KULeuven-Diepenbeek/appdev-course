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

        println("WelcomeActivity--onCreate")
    }

    override fun onStart() {
        super.onStart()
        println("WelcomeActivity--onStart")
    }

    override fun onResume() {
        super.onResume()
        println("WelcomeActivity--onResume")
    }

    override fun onPause() {
        super.onPause()
        println("WelcomeActivity--onPause")
    }

    override fun onStop() {
        super.onStop()
        println("WelcomeActivity--onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("WelcomeActivity--onDestroy")
    }
}