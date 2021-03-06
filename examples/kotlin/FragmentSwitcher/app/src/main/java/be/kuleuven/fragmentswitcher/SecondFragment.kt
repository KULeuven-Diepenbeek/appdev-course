package be.kuleuven.fragmentswitcher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import be.kuleuven.fragmentswitcher.databinding.FragmentSecondBinding
import be.kuleuven.fragmentswitcher.model.MySharedData

class SecondFragment(val data: MySharedData) : Fragment(R.layout.fragment_second) {

    private lateinit var binding: FragmentSecondBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(layoutInflater)
        binding.txtFragmentSecond.text = "Fragment 2, model: ${data.age}"

        return binding.root
    }

}