package com.cdta.roomexample

import android.app.Application
import androidx.room.Room
import com.cdta.roomexample.db.PersonDB

class PersonApp : Application() {
    val room = Room.databaseBuilder(this, PersonDB::class.java, "person").build()
}