package com.example.chatapp.Adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Listener.OnChatClickListener

import com.example.chatapp.data.TalksData

class TalksAdapter(private val talksData: List<TalksData>,private val onChatClickListener: OnChatClickListener): RecyclerView.Adapter<ChatViewHolder>() {
        var onItemClick:((TalksData)->Unit)={}


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {

        return ChatViewHolder(parent)

    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val data=talksData[position]
        holder.bind(talksData[position])
        holder.itemView.setOnClickListener{
            onChatClickListener.onChatItem(data)
        }
    }

    override fun getItemCount(): Int {
     return talksData.size
    }



}