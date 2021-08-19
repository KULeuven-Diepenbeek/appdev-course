package be.kuleuven.howlongtobeat

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import be.kuleuven.howlongtobeat.databinding.ActivityMainBinding
import be.kuleuven.howlongtobeat.model.GameRepository
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var menuBarToggle: ActionBarDrawerToggle
    private lateinit var gameRepository: GameRepository

    private val navHostFragment: NavHostFragment
        get() = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        gameRepository = GameRepository.defaultImpl(applicationContext)
        setupMenuDrawer()

        setContentView(binding.root)
    }

    private fun setupMenuDrawer() {
        menuBarToggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.menu_open, R.string.menu_close)
        binding.drawerLayout.addDrawerListener(menuBarToggle)
        menuBarToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.mnuClear -> tryToClearAllItems()
                R.id.mnuStats -> showStats()
            }
            true
        }
    }

    private fun clearAllItems() {
        gameRepository.overwrite(listOf())
        val currentActiveFragment = navHostFragment.childFragmentManager.fragments[0] as GameListFragment
        currentActiveFragment.clearAllItems()
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun tryToClearAllItems() {
        AlertDialog.Builder(this)
            .setTitle("Delete all games from the DB")
            .setMessage("Are you sure?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton("Yup") { dialog, _ ->
                clearAllItems()
                dialog.dismiss()
            }
            .setNegativeButton("Nah") { dialog, _ ->
                close(dialog)
            }
            .create()
            .show()
    }

    private fun close(dialog: DialogInterface) {
        dialog.dismiss()
        binding.drawerLayout.closeDrawer(GravityCompat.START)
    }

    private fun showStats() {
        val allGames = gameRepository.load()
        val hoursToBeat = allGames.filter { !it.finished }.map { it.howLongToBeat }.sum()
        val hoursAlreadyBeat = allGames.filter { it.finished }.map { it.howLongToBeat }.sum()
        val percCompleted = if(hoursAlreadyBeat > 0) ((hoursToBeat / hoursAlreadyBeat) * 100).roundToInt() else 0

        AlertDialog.Builder(this)
            .setTitle("Game library stats")
            .setMessage("Total games: ${allGames.size}\nHours still to beat: ${hoursToBeat}\nHours already beat: ${hoursAlreadyBeat}\n\n$percCompleted% total completed.")
            .setIcon(android.R.drawable.ic_dialog_info)
            .setNeutralButton("Nice!") { dialog, _ ->
                close(dialog)
            }
            .create()
            .show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(menuBarToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}