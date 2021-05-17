package com.example.fixturesmanage.dao

import androidx.room.*
import com.example.fixturesmanage.model.Status
import com.example.fixturesmanage.model.Type

@Dao
interface StatusDao {
    @Query("SELECT * FROM statuses")
    fun getAll(): List<Status>

    @Insert
    fun insert(unit: Status)

    @Update
    fun update(unit: Status)

    @Delete
    fun delete(unit: Status)

    @Query("SELECT * FROM statuses WHERE id = :id LIMIT 1")
    fun findUnit(id: Int): Status

    @Query("SELECT * FROM statuses WHERE name = :name")
    fun searchName(name: String): List<Status>

    fun existName(name: String): Boolean {
        val row = searchName(name)
        return row.isNotEmpty()
    }
}