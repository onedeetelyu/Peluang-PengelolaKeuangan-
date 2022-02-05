package org.d3ifcool.peluang.database

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Cash::class], version = 1, exportSchema = false)
abstract class CashDatabase : RoomDatabase() {
    abstract val dao: CashDAO

    companion object {
        @VisibleForTesting
        const val DATABASE_NAME = "cash.db"

        @Volatile
        private var INSTANCE: CashDatabase? = null

        fun getInstance(context: Context): CashDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        CashDatabase::class.java,
                        DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}