package com.example.pharmacie_mobile_as.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pharmacie_mobile_as.util.Constants

@Database(
    entities = [MedicamentEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MedicamentDatabase : RoomDatabase() {

    abstract fun medicamentDao(): MedicamentDao

    companion object {
        @Volatile
        private var INSTANCE: MedicamentDatabase? = null

        fun getDatabase(context: Context): MedicamentDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MedicamentDatabase::class.java,
                    Constants.DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}