package com.example.fixturesmanage.model

import androidx.room.*

@Entity(
    tableName = "fixtures",
//    primaryKeys = ["status", "type", "unit"],
//    foreignKeys = [
//        ForeignKey(
//            entity = Status::class,
//            parentColumns = ["id"],
//            childColumns = ["status"],
//            onDelete = ForeignKey.CASCADE
//        ),
//        ForeignKey(
//            entity = Type::class,
//            parentColumns = ["id"],
//            childColumns = ["type"],
//            onDelete = ForeignKey.CASCADE
//        ),
//        ForeignKey(
//            entity = Unit::class,
//            parentColumns = ["id"],
//            childColumns = ["unit"],
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
)
data class Fixture (

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var name: String,

    var type: Int,

    var status: Int,

    var quantity: Int,

    var unit: Int,
)
