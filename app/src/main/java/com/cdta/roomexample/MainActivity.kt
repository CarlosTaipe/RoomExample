package com.cdta.roomexample

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cdta.roomexample.databinding.ActivityMainBinding
import com.cdta.roomexample.db.Person
import com.cdta.roomexample.db.PersonDB
import com.cdta.roomexample.db.PersonDao
import com.cdta.roomexample.db.PersonRepository
import com.cdta.roomexample.presentation.PersonViewModel
import com.cdta.roomexample.presentation.PersonViewModelFactory
import com.cdta.roomexample.ui.adapter.Adapter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var personViewModel: PersonViewModel
    private lateinit var adapter: Adapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val dao: PersonDao = PersonDB.getInstance(application).personDAO
        val repository = PersonRepository(dao)
        val factory = PersonViewModelFactory(repository)
        personViewModel = ViewModelProvider(this,factory).get(PersonViewModel::class.java)
        binding.myViewModel = personViewModel
        binding.lifecycleOwner = this
        displayPersonList()
        initRecyclerView()
        personViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initRecyclerView(){
        binding.personRecyclerview.layoutManager = LinearLayoutManager(this)
        adapter = Adapter({ selectedItem: Person -> listItemClicked(selectedItem)})
        binding.personRecyclerview.adapter = adapter
        displayPersonList()
    }

    private fun displayPersonList(){
        personViewModel.persons.observe(this, Observer {
            Log.i("MYTAG",it.toString())
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })

    }

    private fun listItemClicked(person: Person){
//        Toast.makeText(this,"Selected name is ${person.name}",Toast.LENGTH_LONG).show()
        personViewModel.initUpdateAndDelete(person)
    }

}