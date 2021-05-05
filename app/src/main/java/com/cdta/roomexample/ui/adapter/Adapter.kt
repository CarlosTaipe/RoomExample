package com.cdta.roomexample.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cdta.roomexample.R
import com.cdta.roomexample.databinding.ListItemBinding
import com.cdta.roomexample.db.Person

class Adapter (private val clicklistener:(Person)->Unit):
        RecyclerView.Adapter<Adapter.ViewHolder>(){

    private val personsList = ArrayList<Person>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.list_item,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return personsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(personsList[position], clicklistener)
    }

    fun setList(persons: List<Person>){
        personsList.clear()
        personsList.addAll(persons)
    }

    class ViewHolder(val binding:ListItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(person: Person, clicklistener:(Person)->Unit){
            binding.nameTextView.text = person.name
            binding.addressTextView.text = person.address
            binding.listItemLayout.setOnClickListener{
                clicklistener(person)
            }
        }
    }

}




