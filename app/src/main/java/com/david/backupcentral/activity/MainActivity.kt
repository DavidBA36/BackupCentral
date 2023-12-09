package com.david.backupcentral.activity

import android.accounts.AccountManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat
import androidx.core.view.GravityCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.david.backupcentral.R
import com.david.backupcentral.databinding.ActivityMainBinding
import com.david.backupcentral.helpers.HttpClient
import com.david.backupcentral.helpers.HttpClientCallback
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import java.util.concurrent.Executors


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,HttpClientCallback<String> {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val executorService = Executors.newFixedThreadPool(4)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.navView.setNavigationItemSelectedListener(this);
        val toolbar = binding.content.toolbar
        val client = HttpClient(executorService,this)
        val params= mapOf("Value" to "test")
        client.Post("https://httpbin.org/post", params,this)

        toolbar.setSubtitle("hola")

        val navView: BottomNavigationView = binding.content.bottomNavView

        val navHostFragment  = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_notifications,
                R.id.navigation_history,
                R.id.navigation_user
            )
        )
        toolbar.setupWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            0 -> {
                //val intent = Intent(this, AjustesActivity::class.java)
                //startActivity(intent)
            }
        }
        return true
    }

    override fun onComplete(result: String) {
        Log.e("runnable",result)
    }

    override fun onError(result: String) {
        Log.e("runnable",result)
    }

    override fun onConnectionFailed(result: String) {
        Log.e("runnable",result)
    }

    override fun onStatus(result: String) {
        Log.e("runnable",result)
    }
}