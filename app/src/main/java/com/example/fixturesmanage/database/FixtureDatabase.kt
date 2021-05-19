package com.example.fixturesmanage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fixturesmanage.dao.FixtureDao
import com.example.fixturesmanage.model.Fixture


@Database(entities = [Fixture::class], version = 1)
abstract class FixtureDatabase : RoomDatabase() {

    abstract fun fixtureDao(): FixtureDao

    companion object {

        private var INSTANCE: FixtureDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): FixtureDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FixtureDatabase::class.java, "fixtures_manage_fixtures.db")
                        .allowMainThreadQueries()
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}