package com.example.fixturesmanage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fixturesmanage.dao.UnitDao
import com.example.fixturesmanage.model.Unit


@Database(entities = [Unit::class], version = 1)
abstract class UnitDatabase : RoomDatabase() {

    abstract fun unitDao(): UnitDao

    companion object {

        private var INSTANCE: UnitDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): UnitDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        UnitDatabase::class.java, "fixtures_manage_units.db")
                        .allowMainThreadQueries()
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}