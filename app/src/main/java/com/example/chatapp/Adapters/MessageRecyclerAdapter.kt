package com.example.chatapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.R.layout.cardmessagesend
import com.example.chatapp.data.messageData
import com.google.firebase.auth.FirebaseAuth

class MessageRecyclerAdapter (private val id:String):RecyclerView.Adapter<MessageRecyclerAdapter.MessageHolder>(){

    private  val VIEW_TYPE_MESSAGE_SENT=1
    private  val VIEW_TYPE_MESSAGE_TO=2

    class MessageHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

    }
    private val diffUtil=object:DiffUtil.ItemCallback<messageData>(){
        override fun areItemsTheSame(oldItem: messageData, newItem: messageData): Boolean {
            // içeridekiler aynı mı ?
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: messageData, newItem: messageData): Boolean {
            // içeridekiler aynı mı ?
            return oldItem==newItem
        }

    }
    var messages:List<messageData>
        get() = recyclerListDiffer.currentList
        set(value)=recyclerListDiffer.submitList(value)

    override fun getItemViewType(position: Int): Int {

        val chat=messages.get(position)
        if (chat.send==FirebaseAuth.getInstance().currentUser?.uid.toString()){ //mesajların kimliğine göre tasarımının secilmesi
            return  VIEW_TYPE_MESSAGE_SENT
        } else{
                return  VIEW_TYPE_MESSAGE_TO
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil) // oluşturulan diff utili kullan

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {

        if (viewType==VIEW_TYPE_MESSAGE_SENT){
            val view= LayoutInflater.from(parent.context).inflate(cardmessagesend,parent,false)
            return MessageHolder(view)
        } else{
            val view= LayoutInflater.from(parent.context).inflate(R.layout.tocardview,parent,false)
            return MessageHolder(view)
        }

    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {

                val message=holder.itemView.findViewById<TextView>(R.id.messageText)
                message.text=messages.get(position).message
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}