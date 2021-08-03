package be.kuleuven.fragmentswitcher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import be.kuleuven.fragmentswitcher.databinding.FragmentSecondBinding
import be.kuleuven.fragmentswitcher.model.MySharedData

class SecondFragment : Fragment(R.layout.fragment_second) {

    private lateinit var binding: FragmentSecondBinding
    private lateinit var data: MySharedData

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSecondBinding.inflate(layoutInflater)

        // use the "elvis operator": if left-hand side is null, provide right-hand side.
        // since arguments is nullable ("?"), always make sure to provide an alternative.
        data = (arguments?.getSerializable("mydata") as MySharedData?) ?: MySharedData()

        binding.txtFragmentSecond.text = "Fragment 2, model: ${data.age}"

        return binding.root
    }

}