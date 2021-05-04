package com.cdta.roomexample

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cdta.roomexample.db.Person

@Dao
interface PersonDao {

    /**
     *get all object in DB
     * Suspend fun is used to execute in other thread what not is the main
     */
    @Query("Select * FROM Person")
    fun getAll():LiveData<List<Person>>

    /**
     *get object by id in DB
     */
    @Query("Select * FROM Person WHERE id = :id")
    suspend fun getById(id:Int): Person

    /**
     *update object in DB
     */
    @Update
    suspend fun update(person: Person)

    /**
     *insert object in DB
     */
    @Insert
    suspend fun insert(people: List<Person>)

    /**
     *delete object in DB
     */
    @Delete
    suspend fun delete(person: Person)
}