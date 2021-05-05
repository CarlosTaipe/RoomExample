package com.cdta.roomexample.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person_data_table")
data class Person (
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "Nombre")
    var name:String,
//    @ColumnInfo(name="Edad")
//    val age: Int,
    @ColumnInfo(name = "Dirección")
    var address: String
)