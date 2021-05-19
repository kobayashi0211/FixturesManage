package com.example.fixturesmanage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fixturesmanage.dao.FixtureDao
import com.example.fixturesmanage.dao.StatusDao
import com.example.fixturesmanage.dao.TypeDao
import com.example.fixturesmanage.dao.UnitDao
import com.example.fixturesmanage.model.Fixture
import com.example.fixturesmanage.model.Status
import com.example.fixturesmanage.model.Type
import com.example.fixturesmanage.model.Unit

@Database(
    entities = [
        Fixture::class,
        Status::class,
        Type::class,
        Unit::class,
    ],
    version = 1
)
abstract class FixturesManageDatabase : RoomDatabase() {

    abstract fun fixtureDao(): FixtureDao
    abstract fun statusDao(): StatusDao
    abstract fun typeDao(): TypeDao
    abstract fun unitDao(): UnitDao

    companion object {

        private var INSTANCE: FixturesManageDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): FixturesManageDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FixturesManageDatabase::class.java, "fixtures_manage.db")
                        .allowMainThreadQueries()
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}