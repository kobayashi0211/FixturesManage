package com.example.fixturesmanage.dao

import androidx.room.*
import com.example.fixturesmanage.model.Fixture

@Dao
interface FixtureDao {
    @Query("SELECT * FROM fixtures")
    fun getAll(): List<Fixture>

    @Insert
    fun insert(fixture: Fixture)

    @Update
    fun update(fixture: Fixture)

    @Delete
    fun delete(fixture: Fixture)

    @Query("SELECT * FROM fixtures WHERE id = :id LIMIT 1")
    fun findFixture(id: Int): Fixture

    @Query("SELECT * FROM fixtures WHERE name = :name")
    fun searchName(name: String): List<Fixture>

    fun existName(name: String): Boolean {
        val row = searchName(name)
        return row.isNotEmpty()
    }
}