package com.gstormdev.diceroller.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.gstormdev.diceroller.db.converters.ListConverter
import com.gstormdev.diceroller.db.dao.RollHistoryDao
import com.gstormdev.diceroller.db.entity.RollHistory

@Database(entities = [RollHistory::class], version = 1)
@TypeConverters(ListConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun rollHistoryDao(): RollHistoryDao

    companion object {
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                                context.applicationContext,
                                AppDatabase::class.java,
                                "app-database"
                        ).build()
                    }
                }
            }
            return instance!!
        }
    }
}