package com.cdta.roomexample.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cdta.roomexample.db.Person

@Dao
interface PersonDao {


    /**
     *insert object in DB
     * Suspend fun is used to execute in other thread what not is the main
     */
    @Insert
    suspend fun insertPerson(person: Person):Long


    /**
     *update object in DB
     */
    @Update
    suspend fun update(person: Person): Int

    /**
     *delete object in DB
     */
    @Delete
    suspend fun delete(person: Person): Int

    /**
     *delete all object in DB
     *
     */
    @Query("Delete FROM person_data_table")
    suspend fun deleteAll():Int

    /**
     *get all object in DB, in this function don't use
     * corutines because data obtened that thread background
     *
     */
    @Query("Select * From person_data_table")
    fun getAllPersons():LiveData<List<Person>>

}