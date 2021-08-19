package be.kuleuven.howlongtobeat

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import be.kuleuven.howlongtobeat.databinding.FragmentGamedetailBinding
import be.kuleuven.howlongtobeat.model.Game
import be.kuleuven.howlongtobeat.model.GameRepository
import com.google.android.material.snackbar.Snackbar

class GameDetailFragment : Fragment(R.layout.fragment_gamedetail) {

    private lateinit var binding: FragmentGamedetailBinding
    private lateinit var gameRepo: GameRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGamedetailBinding.inflate(layoutInflater)

        gameRepo = GameRepository.defaultImpl(requireContext())
        val gameId = arguments?.getSerializable(Game.GAME_ID).toString().toInt()
        val game = gameRepo.find(gameId)

        binding.txtDetailTitle.text = "${game.title}\n${game.cartCode}"
        binding.txtDetailGameStats.text = "${game.howLongToBeat} hr(s) to beat"
        binding.chkDetailFinished.isChecked = game.finished
        binding.chkDetailFinished.setOnClickListener {
            game.finished = binding.chkDetailFinished.isChecked
            gameRepo.update(game)

            Snackbar.make(binding.root, "Saved!", Snackbar.LENGTH_SHORT).show()
        }

        requireContext().openFileInput(game.boxartFileName).use {
            binding.imgDetailBoxArt.setImageBitmap(BitmapFactory.decodeStream(it))
        }
        requireContext().openFileInput(game.snapshotFileName).use {
            binding.imgDetailSnapshot.setImageBitmap(BitmapFactory.decodeStream(it))
        }

        return binding.root
    }
}