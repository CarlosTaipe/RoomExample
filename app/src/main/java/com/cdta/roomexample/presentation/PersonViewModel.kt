package com.cdta.roomexample.presentation

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cdta.roomexample.db.Person
import com.cdta.roomexample.db.PersonRepository
import com.cdta.roomexample.ui.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PersonViewModel (private val repository: PersonRepository): ViewModel(), Observable{
    val persons = repository.persons

    private var isUpdateOrDelete = false
    private lateinit var personToUpdateOrDelete: Person

    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputAge = MutableLiveData<String>()

    @Bindable
    val inputAddress = MutableLiveData<String>()

    @Bindable
    var saveOrUpdateBtnText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteBtnText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message : LiveData<Event<String>>
    get()=statusMessage




    /**
     * this text the user will see in the button
     */

    init {
        saveOrUpdateBtnText.value = "Save"
        clearAllOrDeleteBtnText.value = "Clear All"
    }

    fun saveOrUpdate(){
        if (isUpdateOrDelete){
            personToUpdateOrDelete.name = inputName.value!!
            personToUpdateOrDelete.address = inputAddress.value!!
            update(personToUpdateOrDelete)
        }else{
            val name:String = inputName.value!!
//        val age:Int = inputAge.toString()
            val address:String = inputAddress.value!!

            insert(Person(0,name,address))
            inputName.value = null
            inputAddress.value = null
        }


    }

    fun clearAllOrDelete(){
        if (isUpdateOrDelete){
            delete(personToUpdateOrDelete)
        }else{
            clearAll()
        }

    }

    fun insert(person: Person):Job = viewModelScope.launch {
            repository.insert(person)
        statusMessage.value = Event("Person Inserted Succesfully")

    }

    fun update(person: Person):Job = viewModelScope.launch {
        repository.update(person)
        inputName.value = null
        inputAddress.value = null
        isUpdateOrDelete = false
        personToUpdateOrDelete = person
        saveOrUpdateBtnText.value = "Save"
        clearAllOrDeleteBtnText.value = "Clear All"
        statusMessage.value = Event("Person Update Succesfully")
    }

    fun delete(person: Person):Job = viewModelScope.launch {
        repository.delete(person)
        inputName.value = null
        inputAddress.value = null
        isUpdateOrDelete = false
        personToUpdateOrDelete = person
        saveOrUpdateBtnText.value = "Save"
        clearAllOrDeleteBtnText.value = "Clear All"
        statusMessage.value = Event("Person Deleted Succesfully")
    }

    fun clearAll():Job = viewModelScope.launch {
        repository.deleteAll()
        statusMessage.value = Event("All Person Deleted Succesfully")
    }

    fun initUpdateAndDelete(person: Person){
        inputName.value = person.name
        inputAddress.value = person.address
        isUpdateOrDelete = true
        personToUpdateOrDelete = person
        saveOrUpdateBtnText.value = "Update"
        clearAllOrDeleteBtnText.value = "Delete"
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

}
