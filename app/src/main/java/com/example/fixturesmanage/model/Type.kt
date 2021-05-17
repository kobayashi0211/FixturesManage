package com.example.fixturesmanage.model

import androidx.room.*

@Entity(tableName = "types")
data class Type (

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var name: String,
)
