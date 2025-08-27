package com.example.binlookupapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BinHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(binHistory: BinHistoryEntity)

    @Query("SELECT * FROM bin_history ORDER BY timestamp DESC")
    fun getAll(): Flow<List<BinHistoryEntity>>
}