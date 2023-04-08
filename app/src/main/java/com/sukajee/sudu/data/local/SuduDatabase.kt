package com.sukajee.sudu.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sukajee.sudu.data.model.Sudu

@Database(entities = [Sudu::class],  version = 1, exportSchema = false)
abstract class SuduDatabase: RoomDatabase() {
    abstract fun dao(): SuduDao
}
