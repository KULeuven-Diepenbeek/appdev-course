package be.kuleuven.howlongtobeat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import be.kuleuven.howlongtobeat.databinding.FragmentGamelistBinding
import be.kuleuven.howlongtobeat.model.Game
import be.kuleuven.howlongtobeat.model.GameRepository

class GameListFragment : Fragment(R.layout.fragment_gamelist) {

    private val gameList = arrayListOf<Game>()

    private lateinit var binding: FragmentGamelistBinding
    private lateinit var main: MainActivity
    private lateinit var adapter: GameListAdapter
    private lateinit var gameRepository: GameRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGamelistBinding.inflate(layoutInflater)
        main = activity as MainActivity
        gameRepository = GameRepository.defaultImpl(main.applicationContext)
        loadGames()

        adapter = GameListAdapter(gameList)
        binding.rvGameList.adapter = adapter
        binding.rvGameList.layoutManager = LinearLayoutManager(this.context)

        binding.btnAddTodo.setOnClickListener {
            findNavController().navigate(R.id.action_gameListFragment_to_loadingFragment)
        }
        return binding.root
    }

    private fun loadGames() {
        gameList.clear()
        gameList.addAll(gameRepository.load())
        if(!gameList.any()) {
            gameList.add(Game.NONE_YET)
        }
    }

    fun selectGame(game: Game) {
        findNavController().navigate(R.id.action_gameListFragment_to_gameDetailFragment, bundleOf(Game.GAME_ID to game.id.toString()))
    }

    fun clearAllItems() {
        gameList.clear()
        gameList.add(Game.NONE_YET)

        adapter.notifyDataSetChanged()
    }

}