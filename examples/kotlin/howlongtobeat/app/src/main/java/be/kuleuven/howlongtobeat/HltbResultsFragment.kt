package be.kuleuven.howlongtobeat

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
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
import java.io.FileInputStream

class HltbResultsFragment : Fragment(R.layout.fragment_hltbresults) {
    private lateinit var cachedSnapshotPath: Uri
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
        cachedSnapshotPath = Uri.parse(arguments?.getSerializable(HowLongToBeatResult.SNAPSHOT_URI) as String)

        gameRepository = GameRepository.defaultImpl(requireContext())

        adapter = HltbResultsAdapter(resultFromLoadingFragment)
        binding.rvHltbResult.adapter = adapter
        binding.rvHltbResult.layoutManager = LinearLayoutManager(this.context)

        return binding.root
    }

    fun addResultToGameLibrary(hltbResult: HowLongToBeatResult, downloadedBoxart: Bitmap) {
        val game = Game(hltbResult)
        downloadedBoxart.save(game.boxartFileName, requireContext())
        copyCachedSnapshotTo(game.snapshotFileName)
        gameRepository.save(game)

        Snackbar.make(requireView(), "Added ${hltbResult.title} to library!", Snackbar.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_hltbResultsFragment_to_gameListFragment)
    }

    private fun copyCachedSnapshotTo(destination: String) {
        val inStream = requireContext().contentResolver.openInputStream(cachedSnapshotPath) as FileInputStream
        inStream.use {
            requireContext().openFileOutput(destination, Context.MODE_PRIVATE).use { outStream ->
                inStream.channel.transferTo(0, inStream.channel.size(), outStream.channel)
            }
        }
    }
}