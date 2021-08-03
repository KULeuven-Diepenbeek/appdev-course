package be.kuleuven.fragmentswitcher

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import be.kuleuven.fragmentswitcher.databinding.ActivityMainBinding
import be.kuleuven.fragmentswitcher.model.MySharedData

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // share a single instance to communicate between fragments
        val model = MySharedData()
        val firstFragment = FirstFragment(model)
        val secondFragment = SecondFragment(model)

        binding.btnFragment1.setOnClickListener { switchTo(firstFragment) }
        binding.btnFragment2.setOnClickListener { switchTo(secondFragment) }
        switchTo(firstFragment)
    }

    private fun switchTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer, fragment)
            // if you want to add it to the "back stack" to support the back button, also call this.
            addToBackStack("Fragment_${fragment.id}")
            commit()
        }
    }
}