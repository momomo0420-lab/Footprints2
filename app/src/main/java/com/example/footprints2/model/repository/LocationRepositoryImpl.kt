package com.example.footprints2.model.repository

import android.location.Location
import com.example.footprints2.model.repository.api.MyLocationClient
import com.example.footprints2.model.repository.database.MyLocation
import com.example.footprints2.model.repository.database.MyLocationDao
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
     * 現在地と住所（文字列）をDBに登録
     */
    override suspend fun insert(location: Location, address: String) {
        val now = System.currentTimeMillis()

        val myLocation = MyLocation(
            latitude = location.latitude,
            longitude = location.longitude,
            address = address,
            dateAndTime = now
        )

        withContext(Dispatchers.IO) {
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
     * 現在地を取得
     */
    override fun getCurrentLocation(listener: (Location) -> Unit) {
        client.getCurrentLocation(listener)
    }



    /**
     * DBに登録されているMyLocationを全件取得
     *
     * @return MyLocationリスト
     */
    override fun loadAll(): Flow<List<MyLocation>> {
        return dao.loadAll()
    }

    /**
     * DBに登録されている最後に取得した住所を取得
     *
     * @return MyLocation
     */
    override suspend fun loadLastAddress(): String  = withContext(Dispatchers.IO) {
        dao.loadLastAddress()
    }
}