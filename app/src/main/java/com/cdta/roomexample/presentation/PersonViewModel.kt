package com.cdta.roomexample.presentation

import android.util.Patterns
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
import java.util.regex.Pattern

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
        if(inputName.value == null){
            statusMessage.value = Event("Please Enter Person's name")
        }else if(inputAddress.value == null){
            statusMessage.value = Event("Please Enter Person's addres")
        }else {
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

    }

    fun clearAllOrDelete(){
        if (isUpdateOrDelete){
            delete(personToUpdateOrDelete)
        }else{
            clearAll()
        }

    }

    fun insert(person: Person):Job = viewModelScope.launch {
           val newRowID:Long =  repository.insert(person)
        if (newRowID>-1) {
            statusMessage.value = Event("Person Inserted Succesfully $newRowID")
        }else{
            statusMessage.value = Event("Error Ocurred")
        }
    }

    fun update(person: Person):Job = viewModelScope.launch {
        val noOfRows:Int = repository.update(person)
        if (noOfRows>0){
        inputName.value = null
        inputAddress.value = null
        isUpdateOrDelete = false
        personToUpdateOrDelete = person
        saveOrUpdateBtnText.value = "Save"
        clearAllOrDeleteBtnText.value = "Clear All"
        statusMessage.value = Event("$noOfRows Person Update Succesfully")
        }else{
            statusMessage.value = Event("Error Ocurred")
        }
    }

    fun delete(person: Person):Job = viewModelScope.launch {
        val noOfRowsDeleted:Int = repository.delete(person)
        if (noOfRowsDeleted>0) {
            inputName.value = null
            inputAddress.value = null
            isUpdateOrDelete = false
            personToUpdateOrDelete = person
            saveOrUpdateBtnText.value = "Save"
            clearAllOrDeleteBtnText.value = "Clear All"
            statusMessage.value = Event("$noOfRowsDeleted Row Deleted Succesfully")
        }else{
            statusMessage.value = Event("Error Ocurred")
        }
    }

    fun clearAll():Job = viewModelScope.launch {
        val noOfRowsDeleted:Int = repository.deleteAll()
        if (noOfRowsDeleted>0) {
            statusMessage.value = Event("$noOfRowsDeleted Person Deleted Succesfully")
        }else{
            statusMessage.value = Event("Error Ocurred")
        }
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
