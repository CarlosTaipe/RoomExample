package com.cdta.roomexample.db

class PersonRepository (private val dao: PersonDao){

    val persons = dao.getAllPersons()

    suspend fun insert(person: Person){
        dao.insertPerson(person)
    }

    suspend fun update(person: Person){
        dao.update(person)
    }

    suspend fun delete(person: Person){
        dao.delete(person)
    }

    suspend fun deleteAll(person: Person){
        dao.deleteAll()
    }

}