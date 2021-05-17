package com.example.fixturesmanage.dao

import androidx.room.*
import com.example.fixturesmanage.model.Type

@Dao
interface TypeDao {
    @Query("SELECT * FROM types")
    fun getAll(): List<Type>

    @Insert
    fun insert(unit: Type)

    @Update
    fun update(unit: Type)

    @Delete
    fun delete(unit: Type)

    @Query("SELECT * FROM types WHERE id = :id LIMIT 1")
    fun findUnit(id: Int): Type

    @Query("SELECT * FROM types WHERE name = :name")
    fun searchName(name: String): List<Type>

    fun existName(name: String): Boolean {
        val row = searchName(name)
        return row.isNotEmpty()
    }
}