package com.example.fixturesmanage.dao

import androidx.room.*
import com.example.fixturesmanage.model.Unit

@Dao
interface UnitDao {
    @Query("SELECT * FROM units")
    fun getAll(): List<Unit>

    @Insert
    fun insert(unit: Unit)

    @Update
    fun update(unit: Unit)

    @Delete
    fun delete(unit: Unit)

    @Query("SELECT * FROM units WHERE id = :id LIMIT 1")
    fun findUnit(id: Int): Unit

    @Query("SELECT * FROM units WHERE name = :name")
    fun searchName(name: String): List<Unit>

    fun existName(name: String): Boolean {
        val row = searchName(name)
        return row.isNotEmpty()
    }

    @Query("SELECT * FROM units WHERE name LIKE :name")
    fun includeName(name: String): List<Unit>
}