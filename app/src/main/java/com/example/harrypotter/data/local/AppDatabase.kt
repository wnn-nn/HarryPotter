package com.example.harrypotter.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
//import androidx.room.DatabaseConfiguration
import androidx.room.RoomDatabase
//import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [UserEntity::class, FavoriteEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "harry_potter_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}