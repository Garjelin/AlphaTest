package com.example.binlookupapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BinHistoryEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun binHistoryDao(): BinHistoryDao
}