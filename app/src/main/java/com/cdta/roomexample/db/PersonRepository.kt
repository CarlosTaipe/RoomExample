package com.cdta.roomexample.db

class PersonRepository (private val dao: PersonDao){

    val persons = dao.getAllPersons()

    suspend fun insert(person: Person):Long{
        return dao.insertPerson(person)
    }

    suspend fun update(person: Person):Int{
        return dao.update(person)
    }

    suspend fun delete(person: Person):Int{
        return dao.delete(person)
    }

    suspend fun deleteAll():Int{
        return dao.deleteAll()
    }

}