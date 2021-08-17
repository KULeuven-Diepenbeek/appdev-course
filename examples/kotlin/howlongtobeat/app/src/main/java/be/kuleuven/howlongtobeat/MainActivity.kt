package be.kuleuven.howlongtobeat

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import be.kuleuven.howlongtobeat.cartridges.CartridgesRepository
import be.kuleuven.howlongtobeat.databinding.ActivityMainBinding
import be.kuleuven.howlongtobeat.google.GoogleVisionClient
import be.kuleuven.howlongtobeat.hltb.HLTBClient
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var menuBarToggle: ActionBarDrawerToggle
    private var todoFragment = TodoFragment()
    private var loadingFragment = LoadingFragment()
    private lateinit var hltbClient: HLTBClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setCurrentFragment(loadingFragment)
        setupMenuDrawer()

        hltbClient = HLTBClient(applicationContext) {
            todoFragment.onHltbGamesRetrieved(it)
        }

        val cartRepo = CartridgesRepository.fromAsset(applicationContext)
        val sml2SampleData = BitmapFactory.decodeResource(resources, R.drawable.supermarioland2)
        MainScope().launch{
            val cartCode = GoogleVisionClient().findCartCodeViaGoogleVision(sml2SampleData)
            val foundCart = cartRepo.find(cartCode)

            withContext(Dispatchers.Main) {
                Snackbar.make(binding.root, "Found cart: ${foundCart.title}!", Snackbar.LENGTH_LONG).show()
            }
            setCurrentFragment(todoFragment)
            hltbClient.triggerFind(foundCart.title)
        }

        setContentView(binding.root)
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frmTodoContainer, fragment)
            commit()
        }
    }

    private fun setupMenuDrawer() {
        menuBarToggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.menu_open, R.string.menu_close)
        binding.drawerLayout.addDrawerListener(menuBarToggle)
        // it's now ready to be used
        menuBarToggle.syncState()

        // when the menu drawer opens, the toggle button moves to a "back" button and it will close again.
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // handle menu drawer item clicks.
        // since these are all events that influence the fragment list, delegate their actions!
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
        todoFragment.clearAllItems()
    }

    private fun clearLatestItem() {
        todoFragment.clearLatestItem()
    }

    private fun resetItems() {
        todoFragment.resetItems()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // we need to do this to respond correctly to clicks on menu items, otherwise it won't be caught
        if(menuBarToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    fun hideKeyboard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}