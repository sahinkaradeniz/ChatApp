package com.example.chatapp.Adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.data.ContactData

class ContacRvAdapter(val contactdata:List<ContactData>):RecyclerView.Adapter<ContacViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContacViewHolder {
        return ContacViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ContacViewHolder, position: Int) {
       holder.bind(contactdata[position])
    }

    override fun getItemCount(): Int {
        return contactdata.size
    }
}