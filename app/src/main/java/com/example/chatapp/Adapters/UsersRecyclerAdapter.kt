package com.example.chatapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Listener.OnChatClickListener
import com.example.chatapp.R
import com.example.chatapp.data.Chat
import com.squareup.picasso.Picasso


class UsersRecyclerAdapter(private val onChatClickListener: OnChatClickListener) :RecyclerView.Adapter<UsersRecyclerAdapter.UserHolder>(){
    var onItemClick:((Chat)->Unit)={}
    class UserHolder(itemView:View):RecyclerView.ViewHolder(itemView){
    }
    private val diffUtil=object :DiffUtil.ItemCallback<Chat>(){ // sürekli olarak tüm verileri çekmektense sadece yenilenen verileri göstermek için
        override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            // içeridekiler aynı mı ?
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
            return oldItem==newItem
        }
    }
        var users:List<Chat>
        get() = recyclerListDiffer.currentList
        set(value)=recyclerListDiffer.submitList(value)
    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil) // oluşturulan diff utili kullan

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.cardviewtalks,parent,false)
        return UserHolder(view)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
            val name=holder.itemView.findViewById<TextView>(R.id.talksName)
            val message=holder.itemView.findViewById<TextView>(R.id.talksmessage)
            val image=holder.itemView.findViewById<com.github.siyamed.shapeimageview.CircularImageView>(R.id.EditImage)
            val data=users[position]
        name.text=users.get(position).name
        message.text=users.get(position).message
        Picasso.get().load(users.get(position).profileImage).into(image)
        holder.itemView.setOnClickListener {
           onChatClickListener.onChatItem(data)
        }
    }

    override fun getItemCount(): Int {
        return users.size
    }
}




