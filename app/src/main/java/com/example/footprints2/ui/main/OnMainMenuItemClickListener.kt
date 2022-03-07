package com.example.footprints2.ui.main

import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.example.footprints2.R

class OnMainMenuItemClickListener(val viewModel: MainViewModel) : Toolbar.OnMenuItemClickListener {
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        item ?: return false

        var result = true

        when(item.itemId) {
            R.id.action_start -> {
                onStartClick()
            }
            R.id.action_stop -> {
                onStopClick()
            }
            R.id.action_all_delete -> {
                onAllDeleteClick()
            }
            else -> {
                result = false
            }
        }

        return result
    }

    /**
     * スタートボタンが押された際の動作
     */
    private fun onStartClick() {
        viewModel.startWorkManager()
    }

    /**
     * ストップボタンが押された際の動作
     */
    private fun onStopClick() {
        viewModel.stopWorkManager()
    }

    /**
     * 削除ボタン押下時
     */
    private fun onAllDeleteClick() {
        viewModel.deleteLocationList()
    }
}