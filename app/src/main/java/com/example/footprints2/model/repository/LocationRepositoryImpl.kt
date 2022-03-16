package com.example.footprints2.model.repository

import android.location.Location
import com.example.footprints2.model.repository.api.MyLocationClient
import com.example.footprints2.model.repository.database.MyLocation
import com.example.footprints2.model.repository.database.MyLocationDao
import com.example.footprints2.util.DateManipulator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * ロケーションリポジトリ
 *　データの一括管理を行う
 *
 * @property client ロケーションクライアント
 * @property dao ロケーションDAO
 */
class LocationRepositoryImpl @Inject constructor(
    private val client: MyLocationClient,
    private val dao: MyLocationDao
) : LocationRepository {
    /**
     * 現在地を取得
     */
    override fun getCurrentLocation(listener: (Location?) -> Unit) {
        client.getCurrentLocation(listener)
    }

    /**
     * 現在地と住所（文字列）をDBに登録
     */
    override suspend fun insert(location: Location, address: String) {
        withContext(Dispatchers.IO) {
            val lastAddress = loadLastAddress()

            if(lastAddress == address) {
                return@withContext
            }

            val now = System.currentTimeMillis()

            val myLocation = MyLocation(
                latitude = location.latitude,
                longitude = location.longitude,
                address = address,
                date = DateManipulator.convertTimeStampToDate(now),
                time = DateManipulator.convertTimeStampToTime(now)
            )

            dao.insert(myLocation)
        }
    }

    /**
     * DBの全件削除
     */
    override suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            dao.deleteAll()
        }
    }

    /**
     * DBに登録されている日付を全件取得
     *
     * @return 日付リスト
     */
    override fun loadAllDate(): Flow<List<String>> {
        return dao.loadAllDate()
    }

    /**
     * 日付からMyLocationを取得する
     *
     * @param date 日付
     * @return MyLocationリスト
     */
    override suspend fun loadMyLocationFrom(date: String): List<MyLocation> {
        return withContext(Dispatchers.IO) {
            dao.loadMyLocationFrom(date)
        }
    }

    /**
     * DBに登録されている最後に取得した住所を取得
     *
     * @return 住所
     */
    override suspend fun loadLastAddress(): String  = withContext(Dispatchers.IO) {
        dao.loadLastAddress()
    }
}