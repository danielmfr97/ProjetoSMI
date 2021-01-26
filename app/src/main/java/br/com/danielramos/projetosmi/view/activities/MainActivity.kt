package br.com.danielramos.projetosmi.view.activities

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import br.com.danielramos.projetosmi.MyApplication
import br.com.danielramos.projetosmi.R
import br.com.danielramos.projetosmi.databinding.ActivityMainBinding
import br.com.danielramos.projetosmi.view.fragments.DashboardFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    init {
        instance = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestUserPermissions()
    }


    private fun requestUserPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS
        )

        var isAllPermissionsGranted = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (permission in permissions) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) isAllPermissionsGranted = false
            }
            if (isAllPermissionsGranted) init() else ActivityCompat.requestPermissions(
                this,
                permissions,
                101
            )
        } else init()
    }

    fun init() {
        val view = binding.root
        setContentView(view)
        configureToolbar()
        configureDrawer()
        configureNavController()
    }

    private fun configureToolbar() {
        setSupportActionBar(binding.toolbarMain)
    }

    private fun configureDrawer() {
        binding.navView.setNavigationItemSelectedListener(this)
        val toogle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbarMain,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toogle)
        toogle.syncState()
        actionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setHomeButtonEnabled(true)
        }
    }

    private fun configureNavController() {
        navController = findNavController(R.id.navHostFragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        // Informa para respeitar a pilha do navHostFragment
        // O onSupportNavigateUp faz com que seja respeitado as activities no androidManifest, caso haja mais de uma
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_dashboard -> navController.navigate(R.id.action_open_dashboard_fragment)
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.call_menu -> Toast.makeText(this, "Making a Call", Toast.LENGTH_SHORT).show()
        }
        return true
    }



    companion object {
        private var instance: MainActivity? = null

//        fun setFragment(fragment: Fragment) {
//            val transaction = instance!!.supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.fragment_container, fragment)
//                .addToBackStack(null)
//                .commit()
//        }
//
//        fun moveToDashboard() {
//            val transaction = instance!!.supportFragmentManager.beginTransaction()
//            transaction.replace(R.id.fragment_container, DashboardFragment.newInstance())
//                .addToBackStack(null)
//                .commit()
//        }
    }
}