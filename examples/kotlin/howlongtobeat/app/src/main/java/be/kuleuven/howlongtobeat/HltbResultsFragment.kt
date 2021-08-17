package be.kuleuven.howlongtobeat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import be.kuleuven.howlongtobeat.databinding.FragmentHltbresultsBinding
import be.kuleuven.howlongtobeat.hltb.HowLongToBeatResult

class HltbResultsFragment : Fragment(R.layout.fragment_hltbresults) {
    private lateinit var binding: FragmentHltbresultsBinding
    private lateinit var adapter: HltbResultsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHltbresultsBinding.inflate(layoutInflater)

        val resultFromLoadingFragment = arguments?.getSerializable(HowLongToBeatResult.RESULT) as List<HowLongToBeatResult>

        adapter = HltbResultsAdapter(resultFromLoadingFragment)
        binding.rvHltbResult.adapter = adapter
        binding.rvHltbResult.layoutManager = LinearLayoutManager(this.context)

        return binding.root
    }
}