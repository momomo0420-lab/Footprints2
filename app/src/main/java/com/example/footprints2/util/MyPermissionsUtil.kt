package com.example.footprints2.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

object MyPermissionsUtil {
    enum class PermissionsState {
        ALL_GRANTED,
        DO_NOT_HAVE_ONLY_BACKGROUND_LOCATION,
        UNAUTHORIZED
    }

    /**
     * 必要な権限を保持しているか確認する
     *
     * @return 確認結果
     *          ALL_GRANTED - 権限をすべて保持している
     *          DO_NOT_HAVE_ONLY_BACKGROUND_LOCATION - バックグラウンドロケーションの権限だけ保持していない
     *          UNAUTHORIZED - 権限を保持していない
     */
    fun checkRequiredPermissions(
        context: Context,
        requiredPermissions: Array<String>
    ) : PermissionsState {
        var result = PermissionsState.ALL_GRANTED

        var errorCount = 0
        var errorPermission = ""

        for(requiredPermission in requiredPermissions) {
            val checkResult = ContextCompat.checkSelfPermission(
                context,
                requiredPermission)

            if(checkResult != PackageManager.PERMISSION_GRANTED) {
                result = PermissionsState.UNAUTHORIZED
                errorPermission = requiredPermission
                errorCount++
                break
            }
        }

        if((errorCount == 1)
            && (errorPermission == Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
            result = PermissionsState.DO_NOT_HAVE_ONLY_BACKGROUND_LOCATION
        }

        return result
    }
}