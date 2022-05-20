package com.example.chatapp.Adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.Activity.MessagingActivity
import com.example.chatapp.R
import com.example.chatapp.data.TalksData

class ChatViewHolder(conteiner:ViewGroup):RecyclerView.ViewHolder(LayoutInflater.from(conteiner.context).inflate(
    R.layout.cardviewtalks,conteiner,false)){
    val cardview:CardView=itemView.findViewById(R.id.cardview)
    val talksName:TextView=itemView.findViewById(R.id.talksName)
    val talksMessage:TextView=itemView.findViewById(R.id.talksmessage)
    val cardVievhour:TextView=itemView.findViewById(R.id.cardViewhour)
    val TalksimageView:com.github.siyamed.shapeimageview.CircularImageView=itemView.findViewById(R.id.TalksimageView)

    fun bind(newTalksData: TalksData){
        talksName.text=newTalksData.name
        talksMessage.text=newTalksData.Chat
        cardVievhour.text=newTalksData.hour
        TalksimageView.setImageResource(newTalksData.image)
        itemView.setOnClickListener{
            val intent=Intent(it.context,MessagingActivity::class.java)

        }
    }


}


