package com.example.fixturesmanage.model

import androidx.room.*

@Entity(tableName = "units")
data class Unit (

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var name: String,
)
