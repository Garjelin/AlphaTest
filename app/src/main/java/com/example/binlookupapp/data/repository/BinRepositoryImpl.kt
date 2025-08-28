package com.example.binlookupapp.data.repository

import com.example.binlookupapp.data.local.BinHistoryDao
import com.example.binlookupapp.data.local.BinHistoryEntity
import com.example.binlookupapp.data.remote.BinApiService
import com.example.binlookupapp.domain.models.BinHistory
import com.example.binlookupapp.domain.models.BinInfo
import com.example.binlookupapp.domain.repository.BinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException

class BinRepositoryImpl(
    private val api: BinApiService,
    private val dao: BinHistoryDao
) : BinRepository {

    override suspend fun getBinInfo(bin: String): BinInfo {
        try {
            return api.getBinInfo(bin)
        } catch (e: HttpException) {
            when (e.code()) {
                404 -> throw Exception("No data found for BIN")
                429 -> throw Exception("API rate limit exceeded")
                else -> throw Exception("HTTP error: ${e.message()}")
            }
        } catch (e: IOException) {
            throw Exception("Network error: ${e.message}")
        }
    }

    override suspend fun insertHistory(bin: String, info: BinInfo) {
        val entity = BinHistoryEntity(
            bin = bin,
            timestamp = System.currentTimeMillis(),
            scheme = info.scheme,
            type = info.type,
            brand = info.brand,
            countryName = info.country?.name,
            latitude = info.country?.latitude,
            longitude = info.country?.longitude,
            bankName = info.bank?.name,
            bankUrl = info.bank?.url,
            bankPhone = info.bank?.phone,
            bankCity = info.bank?.city
        )
        dao.insert(entity)
    }

    override fun getHistory(): Flow<List<BinHistory>> {
        return dao.getAll().map { entities ->
            entities.map { entity ->
                BinHistory(
                    id = entity.id,
                    bin = entity.bin,
                    timestamp = entity.timestamp,
                    scheme = entity.scheme,
                    type = entity.type,
                    brand = entity.brand,
                    countryName = entity.countryName,
                    latitude = entity.latitude,
                    longitude = entity.longitude,
                    bankName = entity.bankName,
                    bankUrl = entity.bankUrl,
                    bankPhone = entity.bankPhone,
                    bankCity = entity.bankCity
                )
            }
        }
    }
}