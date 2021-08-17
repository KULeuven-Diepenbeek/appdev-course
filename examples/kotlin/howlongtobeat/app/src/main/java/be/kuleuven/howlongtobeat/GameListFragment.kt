package be.kuleuven.howlongtobeat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        gameList.addAll(gameRepository.load())
        if(!gameList.any()) {
            gameList.add(Game.NONE_YET)
        }
    }

    /*
    fun onHltbGamesRetrieved(games: List<HowLongToBeatResult>) {
        gameList.clear()
        gameList.addAll(games.map { Game("${it.title} (${it.howlong})", false) })
        adapter.notifyDataSetChanged()
    }

    fun clearAllItems() {
        gameList.clear()
        adapter.notifyDataSetChanged()
    }

    fun clearLatestItem() {
        if(gameList.size >= 1) {
            gameList.removeAt(gameList.size - 1)
            adapter.notifyItemRemoved(gameList.size - 1)
        }
    }

     */


}