package be.kuleuven.fragmentswitcher

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import be.kuleuven.fragmentswitcher.databinding.FragmentFirstBinding
import be.kuleuven.fragmentswitcher.model.MySharedData

class FirstFragment : Fragment(R.layout.fragment_first) {

    private lateinit var binding: FragmentFirstBinding
    private var data: MySharedData = MySharedData()

    // here the view should be set
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        println("Fragment: onCreateView")
        // this looks a lot like activities! Fragments have their own lifecycle.
        // return inflater.inflate(R.layout.fragment_first, container, false)
        // -- above should NOT be needed since we used a constructor argument!
        binding = FragmentFirstBinding.inflate(layoutInflater)

        updateTextFromModel()
        binding.txtFragmentFirst.setOnClickListener {
            data.age++
            updateTextFromModel()
        }
        binding.btnGoToNext.setOnClickListener {
            // adding an object to a bundle only works with serialization plugins!
            // see the intents parts of the course.
            val bundle = bundleOf("mydata" to data)
            findNavController().navigate(R.id.action_firstFragment_to_secondFragment, bundle)
        }

        // remember to do this instead of super.onCreateView()
        // otherwise nothing will happen.
        return binding.root
    }

    private fun updateTextFromModel() {
        binding.txtFragmentFirst.text = "Fragment 1, model: ${data.age}"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("Fragment: onViewCreated")
    }

    // here the view should NOT be set: onCreateView() is called afterwards.
    // remember that accessing UI components here will crash!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("Fragment: onCreate")
    }
}