package com.example.fixturesmanage.dao

import androidx.room.*
import com.example.fixturesmanage.model.Status

@Dao
interface StatusDao {
    @Query("SELECT * FROM statuses")
    fun getAll(): List<Status>

    @Insert
    fun insert(status: Status)

    @Update
    fun update(status: Status)

    @Delete
    fun delete(status: Status)

    @Query("SELECT * FROM statuses WHERE id = :id LIMIT 1")
    fun findStatus(id: Int): Status

    @Query("SELECT * FROM statuses WHERE name = :name")
    fun searchName(name: String): List<Status>

    fun existName(name: String): Boolean {
        val row = searchName(name)
        return row.isNotEmpty()
    }

    @Query("SELECT * FROM statuses WHERE name LIKE :name")
    fun includeName(name: String): List<Status>
}