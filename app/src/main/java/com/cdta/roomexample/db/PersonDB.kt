package com.cdta.roomexample.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Person::class],
    version = 1
)

abstract class PersonDB: RoomDatabase() {

    abstract val personDAO: PersonDao

    companion object{
        @Volatile
        private var INSTANCE:PersonDB?=null
        fun getInstance(context: Context): PersonDB{
            synchronized(this){
                var instance:PersonDB?= INSTANCE
                if(instance==null){
                    instance= Room.databaseBuilder(
                        context.applicationContext,
                        PersonDB::class.java,
                         "person_data_base"//Create DB
                    ).build()
                }
                return instance
            }
        }
    }
 }