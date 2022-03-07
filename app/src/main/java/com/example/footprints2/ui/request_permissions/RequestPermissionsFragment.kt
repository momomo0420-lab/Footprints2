package com.example.footprints2.ui.request_permissions

import android.Manifest
import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.footprints2.R
import com.example.footprints2.constants.AppConstants
import com.example.footprints2.databinding.FragmentRequestPermissionsBinding
import com.example.footprints2.util.MyPermissionsUtil
import com.example.footprints2.ui.SharedViewModel

class RequestPermissionsFragment : Fragment() {
    companion object {
        private const val REQUEST_BACKGROUND_LOCATION_PERMISSION = 10
    }

    private var _binding: FragmentRequestPermissionsBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRequestPermissionsBinding.inflate(inflater, container, false)
        binding.apply {
            handler = this@RequestPermissionsFragment
            lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAppBar()

        sharedViewModel.hasPermissions.observe(
            viewLifecycleOwner,
            getPermissionsStateObserver()
        )

    }

    /**
     * 必要な権限が満たされたらメイン画面へ遷移する
     */
    private fun getPermissionsStateObserver(): Observer<Boolean> {
        return Observer {
            if(!it) {
                return@Observer
            }
            gotoMainScreen()
        }
    }

    /**
     * アプリバーの設定を行う
     */
    private fun setupAppBar() {
        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar_main_activity)
        toolbar.menu.clear()
    }

    /**
     * 次の画面へ遷移
     * 権限許可されている場合 -> メイン画面へ
     * 許可されていない場合 -> 権限の設定画面へ
     * 一部許可されている場合 -> アプリの詳細設定画面
     */
    private fun gotoNextScreen() {
        val checkResult = MyPermissionsUtil.checkRequiredPermissions(
            requireContext(),
            AppConstants.REQUIRED_PERMISSIONS
        )

        if(checkResult == MyPermissionsUtil.PermissionsState.UNAUTHORIZED) {
            requestRequiredPermissions()
            return
        }

        if(checkResult == MyPermissionsUtil.PermissionsState.DO_NOT_HAVE_ONLY_BACKGROUND_LOCATION) {
            showNotificationDialog(
                getString(R.string.requested_always_allow),
                getListenerForRequestingBackGroundPermissions()
            )
            return
        }

        sharedViewModel.setHasPermissions(true)
    }

    /**
     * メイン画面へ遷移
     */
    private fun gotoMainScreen() {
        val action = RequestPermissionsFragmentDirections.actionRequestPermissionFragmentToMainFragment()
        findNavController().navigate(action)
    }

    /**
     * アプリ詳細設定画面へ遷移
     */
    private fun gotoApplicationDetailScreen() {
        val packageName = requireContext().packageName
        val intent = Intent(
            ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:$packageName")
        )

        startActivity(intent)
    }

    /**
     * 必要な権限（ロケーション）要求結果。
     */
    private val locationPermissionsRequestLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->

        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                showNotificationDialog(
                    getString(R.string.requested_always_allow),
                    getListenerForRequestingBackGroundPermissions()
                )
            }
            else -> {
                showNotificationDialog(
                    getString(R.string.requested_always_allow)
                            + getString(R.string.request_accurate_location),
                    getListenerForTransitioningToDetailScreen()
                )
            }
        }
    }

    /**
     * 必要な権限を要求する
     */
    private fun requestRequiredPermissions() {
        locationPermissionsRequestLauncher.launch(AppConstants.REQUESTBLE_PERMISSIONS)
    }

    /**
     * ボタンクリック時の動作
     */
    fun onClick() {
        gotoNextScreen()
    }

    /**
     * ロケーション設定依頼用のダイアログを表示
     *
     * @param notice 通知文
     * @param positiveClickListener ”はい”押下時の処理リスナー
     */
    private fun showNotificationDialog(
        notice: String,
        positiveClickListener: DialogInterface.OnClickListener
    ) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.apply {
            setTitle("使用上の注意")
            setMessage(notice)
            setPositiveButton("はい", positiveClickListener)
            setNegativeButton("いいえ", null)
        }
        dialogBuilder.create().show()
    }

    /**
     * アプリの詳細画面へ遷移するためのリスナー
     */
    private fun getListenerForTransitioningToDetailScreen(): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { _, _ ->
            gotoApplicationDetailScreen()
        }
    }

    /**
     * バックグラウンド権限を要求するためのリスナー
     */
    private fun getListenerForRequestingBackGroundPermissions(
    ): DialogInterface.OnClickListener {
        return DialogInterface.OnClickListener { _, _ ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(ACCESS_BACKGROUND_LOCATION),
                    REQUEST_BACKGROUND_LOCATION_PERMISSION
                )
            }
        }
    }


}