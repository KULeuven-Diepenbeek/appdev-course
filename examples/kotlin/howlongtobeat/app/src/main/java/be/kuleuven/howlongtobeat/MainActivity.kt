package be.kuleuven.howlongtobeat

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import be.kuleuven.howlongtobeat.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var menuBarToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

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
                R.id.mnuClear -> clearAllItems()
                R.id.mnuClearLatest -> clearLatestItem()
                R.id.mnuReset -> resetItems()
            }
            true
        }
    }

    private fun clearAllItems() {
        //todoFragment.clearAllItems()
    }

    private fun clearLatestItem() {
        //todoFragment.clearLatestItem()
    }

    private fun resetItems() {
        //todoFragment.resetItems()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(menuBarToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}