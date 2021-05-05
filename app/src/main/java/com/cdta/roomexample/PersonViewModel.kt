package com.cdta.roomexample

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cdta.roomexample.db.Person
import com.cdta.roomexample.db.PersonRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PersonViewModel (private val repository: PersonRepository): ViewModel(), Observable{
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
    }

    val persons = repository.persons

    @Bindable
    val inputName = MutableLiveData<String>()

    @Bindable
    val inputAge = MutableLiveData<String>()

    @Bindable
    val inputAddress = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateBtnText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteBtnText = MutableLiveData<String>()

    /**
     * this text the user will see in the button
     */

    init {
        saveOrUpdateBtnText.value = "Save"
        clearAllOrDeleteBtnText.value = "Clear All"
    }

    fun saveOrUpdate(){
        val name:String = inputName.value!!
//        val age:Int = inputAge.toString()
        val address:String = inputAddress.value!!

        insert(Person(0,name,address))
        inputName.value = null
        inputAddress.value = null

    }

    fun clearAllOrDelete(){
        clearAll()
    }

    fun insert(person: Person):Job = viewModelScope.launch {
            repository.insert(person)

    }

    fun update(person: Person):Job = viewModelScope.launch {
        repository.update(person)
    }

    fun delete(person: Person):Job = viewModelScope.launch {
        repository.delete(person)
    }

    fun clearAll():Job = viewModelScope.launch {
        repository.deleteAll()
    }

}
