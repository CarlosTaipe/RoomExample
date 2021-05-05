package com.cdta.roomexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cdta.roomexample.databinding.ActivityMainBinding
import com.cdta.roomexample.db.PersonDB
import com.cdta.roomexample.db.PersonDao
import com.cdta.roomexample.db.PersonRepository
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var personViewModel: PersonViewModel
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
    }

    private fun initRecyclerView(){
        binding.personRecyclerview.layoutManager = LinearLayoutManager(this)
        displayPersonList()
    }

    private fun displayPersonList(){
        personViewModel.persons.observe(this, Observer {
            Log.i("MYTAG",it.toString())
            binding.personRecyclerview.adapter = Adapter(it)
        })

    }
}