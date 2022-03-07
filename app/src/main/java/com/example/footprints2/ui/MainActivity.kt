package com.example.footprints2.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.footprints2.constants.AppConstants
import com.example.footprints2.R
import com.example.footprints2.util.MyPermissionsUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupAppBar()

        // 必要な権限が存在するか確認
        checkRequiredPermissions()
    }

    /**
     * アプリバーの設定を行う
     */
    private fun setupAppBar() {
        val configuration = AppBarConfiguration(
            setOf(R.id.requestPermissionFragment, R.id.mainFragment)
        )

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val controller = navHostFragment.navController
        val toolbar = findViewById<Toolbar>(R.id.toolbar_main_activity)
        toolbar.setupWithNavController(controller, configuration)
    }

    /**
     * 必要な権限が存在するか確認する
     */
    private fun checkRequiredPermissions() {
        val checkResult = MyPermissionsUtil.checkRequiredPermissions(
            this, AppConstants.REQUIRED_PERMISSIONS
        )
        if(checkResult == MyPermissionsUtil.PermissionsState.ALL_GRANTED) {
            viewModel.setHasPermissions(true)
        }
    }
}
