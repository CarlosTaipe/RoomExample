package com.cdta.roomexample.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "person_data_table")
data class Person (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "Nombre")
    val name:String,
//    @ColumnInfo(name="Edad")
//    val age: Int,
    @ColumnInfo(name = "Dirección")
    val address: String
)