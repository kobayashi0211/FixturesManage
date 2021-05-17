package com.example.fixturesmanage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fixturesmanage.dao.StatusDao
import com.example.fixturesmanage.model.Status


@Database(entities = [Status::class], version = 1)
abstract class StatusDatabase : RoomDatabase() {

    abstract fun statusDao(): StatusDao

    companion object {

        private var INSTANCE: StatusDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): StatusDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        StatusDatabase::class.java, "fixtures_manage.db")
                        .allowMainThreadQueries()
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}