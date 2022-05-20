package com.example.chatapp.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.data.ContactData

class ContacViewHolder (container:ViewGroup):RecyclerView.ViewHolder(LayoutInflater.from(container.context).inflate(
    R.layout.cardviewcontac,container,false)){
    val contaccard:CardView=itemView.findViewById(R.id.contaccard)
    val contacname:TextView=itemView.findViewById(R.id.contacname)
    val contacmessage:TextView=itemView.findViewById(R.id.contacmessage)
    val contacimage:com.github.siyamed.shapeimageview.CircularImageView=itemView.findViewById(R.id.contacimage)

    fun bind(contactData: ContactData){
        contacname.text=contactData.ad
        contacmessage.text=contactData.message
        contacimage.setImageResource(contactData.image)
    }
}