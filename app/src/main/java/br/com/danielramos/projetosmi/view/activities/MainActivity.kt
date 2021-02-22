package br.com.danielramos.projetosmi.view.activities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import br.com.danielramos.projetosmi.R
import br.com.danielramos.projetosmi.contracts.MainContract
import br.com.danielramos.projetosmi.databinding.ActivityMainBinding
import br.com.danielramos.projetosmi.presenter.MainPresenter
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, MainContract.MainView {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestUserPermissions()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        instance = this
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
        inicializarPresenter()
    }

    private fun inicializarPresenter() {
        presenter = MainPresenter(this)
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
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            val id = destination.id
            when (id) {
                R.id.startFragment -> binding.toolbarMain.visibility = View.VISIBLE
                R.id.loginFragment -> {
                    binding.toolbarMain.visibility = View.GONE
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                R.id.registerFragment -> {
                    binding.toolbarMain.visibility = View.GONE
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                }
                else -> {
                    binding.toolbarMain.visibility = View.VISIBLE
                    binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
                }
            }
        }
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

    fun openToastShort(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    fun openToastLong(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show()
    }



    companion object {
        var instance: MainActivity? = null

        val context: Context
            get() = instance!!.baseContext
    }
}