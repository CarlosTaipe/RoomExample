package com.cdta.roomexample

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.cdta.roomexample.databinding.ListItemBinding
import com.cdta.roomexample.db.Person

class Adapter (private val personsList: List<Person>):
        RecyclerView.Adapter<Adapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater= LayoutInflater.from(parent.context)
        val binding: ListItemBinding =
            DataBindingUtil.inflate(layoutInflater,R.layout.list_item,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return personsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(personsList[position])
    }

    class ViewHolder(val binding:ListItemBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(person: Person){
            binding.nameTextView.text = person.name
            binding.addressTextView.text = person.address
        }
    }

}



