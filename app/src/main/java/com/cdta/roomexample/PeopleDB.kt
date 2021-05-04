package com.cdta.roomexample

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cdta.roomexample.db.Person

@Database(
    entities = [Person::class],
    version = 1
)

abstract class PeopleDB: RoomDatabase() {

    abstract fun personDao(): PersonDao
}