package com.cdta.roomexample

import androidx.room.*

@Dao
interface PersonDao {

    /**
     *get all object in DB
     */
    @Query("Select * FROM Person")
    fun getAll():List<Person>

    /**
     *get object by id in DB
     */
    @Query("Select * FROM Person WHERE id = :id")
    fun getById(id:Int):Person

    /**
     *update object in DB
     */
    @Update
    fun update(person: Person)

    /**
     *insert object in DB
     */
    @Insert
    fun insert(people: List<Person>)

    /**
     *delete object in DB
     */
    @Delete
    fun delete(person: Person)
}