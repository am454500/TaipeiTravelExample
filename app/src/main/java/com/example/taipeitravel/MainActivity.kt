package com.example.taipeitravel

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.taipeitravel.databinding.ActivityMainBinding
import com.example.taipeitravel.uiAttractions.fragment.AttractionsFragment

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_translate -> {
                findNavController(R.id.nav_host_fragment_content_main).let {
                    val navHostFragment = supportFragmentManager.primaryNavigationFragment
                    navHostFragment!!.childFragmentManager.fragments[0].also {
                        (it as? AttractionsFragment)?.binding?.languagesSpinner?.performClick()
                    }
                }

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    /**
     * 換風景內頁的時候調用 把標題換成風景名稱
     */
    fun setAppBarTitle(title: String) {
        supportActionBar?.title = title
    }

    /**
     * 翻譯按鈕只在列表頁調用
     */
    fun isShowOptionsMenu(show: Boolean) {
        binding.toolbar.menu.findItem(R.id.action_translate)?.isVisible = show
    }
}