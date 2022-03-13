package com.example.footprints2.ui.main

import androidx.lifecycle.*
import androidx.work.*
import com.example.footprints2.model.repository.LocationRepository
import com.example.footprints2.worker.LocationUpdateWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: LocationRepository,
    private val workManager: WorkManager
) : ViewModel() {
    companion object {
        private const val TAG = "MainViewModel"
    }

    // 実行可能状態の確認フラグ
    private val _isRunnable = MutableLiveData(true)
    val isRunnable: LiveData<Boolean> get() = _isRunnable

    // 保持されているMyLocation全件
    val dateList = repository.loadAllDate().asLiveData()

    val errorCode = MutableLiveData(0)

    /**
     * ワーカーが登録されているか確認する
     */
    fun confirmWorkStartUp() {
        val workInfoList = workManager.getWorkInfosForUniqueWork(
            LocationUpdateWorker.UNIQUE_WORK_NAME
        ).get()

        var count = 0

        if(workInfoList.isNotEmpty()) {
            _isRunnable.value = false
            count++

            for(work in workInfoList) {
                if(work.state != WorkInfo.State.ENQUEUED) {
                    _isRunnable.value = true
                    count++
                }
            }
        }
        errorCode.value = count
    }

    /**
     * ワーカーを実行
     */
    fun startWorkManager() {
        workManager.enqueueUniquePeriodicWork(
            LocationUpdateWorker.UNIQUE_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            createWorkerRequest()
        )
        _isRunnable.value = false
    }

    /**
     * ワークリクエスト作成
     */
    private fun createWorkerRequest(): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<LocationUpdateWorker>(
            15, TimeUnit.MINUTES
        ).build()
    }

    /**
     * ワーカーを停止
     */
    fun stopWorkManager() {
        workManager.cancelUniqueWork(LocationUpdateWorker.UNIQUE_WORK_NAME)
        _isRunnable.value = true
    }


    /**
     * ロケーションリストを削除する
     */
    fun deleteLocationList() {
        viewModelScope.launch {
            repository.deleteAll()
        }
    }

}