package com.example.fixturesmanage.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fixturesmanage.dao.TypeDao
import com.example.fixturesmanage.model.Type


@Database(entities = [Type::class], version = 1)
abstract class TypeDatabase : RoomDatabase() {

    abstract fun typeDao(): TypeDao

    companion object {

        private var INSTANCE: TypeDatabase? = null

        private val lock = Any()

        fun getInstance(context: Context): TypeDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        TypeDatabase::class.java, "fixtures_manage.db")
                        .allowMainThreadQueries()
                        .build()
                }
                return INSTANCE!!
            }
        }
    }
}