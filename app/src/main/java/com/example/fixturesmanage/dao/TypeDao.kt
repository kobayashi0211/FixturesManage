package com.example.fixturesmanage.dao

import androidx.room.*
import com.example.fixturesmanage.model.Type

@Dao
interface TypeDao {
    @Query("SELECT * FROM types")
    fun getAll(): List<Type>

    @Insert
    fun insert(type: Type)

    @Update
    fun update(type: Type)

    @Delete
    fun delete(type: Type)

    @Query("SELECT * FROM types WHERE id = :id LIMIT 1")
    fun findType(id: Int): Type

    @Query("SELECT * FROM types WHERE name = :name")
    fun searchName(name: String): List<Type>

    fun existName(name: String): Boolean {
        val row = searchName(name)
        return row.isNotEmpty()
    }

    @Query("SELECT * FROM types WHERE name LIKE :name")
    fun includeName(name: String): List<Type>
}