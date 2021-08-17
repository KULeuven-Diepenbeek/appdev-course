package be.kuleuven.howlongtobeat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import be.kuleuven.howlongtobeat.databinding.FragmentHltbresultsBinding
import be.kuleuven.howlongtobeat.hltb.HowLongToBeatResult
import be.kuleuven.howlongtobeat.model.Game
import be.kuleuven.howlongtobeat.model.GameRepository
import com.google.android.material.snackbar.Snackbar

class HltbResultsFragment : Fragment(R.layout.fragment_hltbresults) {
    private lateinit var binding: FragmentHltbresultsBinding
    private lateinit var adapter: HltbResultsAdapter
    private lateinit var gameRepository: GameRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHltbresultsBinding.inflate(layoutInflater)

        val resultFromLoadingFragment = arguments?.getSerializable(HowLongToBeatResult.RESULT) as List<HowLongToBeatResult>

        gameRepository = GameRepository.defaultImpl(requireContext())

        adapter = HltbResultsAdapter(resultFromLoadingFragment)
        binding.rvHltbResult.adapter = adapter
        binding.rvHltbResult.layoutManager = LinearLayoutManager(this.context)

        return binding.root
    }

    fun addResultToGameLibrary(hltbResult: HowLongToBeatResult) {
        gameRepository.save(Game(hltbResult))
        Snackbar.make(requireView(), "Added ${hltbResult.title} to library!", Snackbar.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_hltbResultsFragment_to_gameListFragment)
    }
}