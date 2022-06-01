package com.example.chatapp.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.chatapp.R
import com.example.chatapp.data.Forum
import com.squareup.picasso.Picasso

class ForumRecyclerViewAdapter :RecyclerView.Adapter<ForumRecyclerViewAdapter.ForumHolder>(){
    private  val VIEW_TYPE_TEXT = 1
    private  val VIEW_TYPE_IMAGE=2

    class ForumHolder(itemView:View):RecyclerView.ViewHolder(itemView){
    }

    private val diffUtil=object :DiffUtil.ItemCallback<Forum>(){
        override fun areItemsTheSame(oldItem: Forum, newItem: Forum): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Forum, newItem: Forum): Boolean {
            return oldItem==newItem
        }
    }

    var forums:List<Forum>
        get() =recyclerListDiffer.currentList
        set(value)=recyclerListDiffer.submitList(value)

    override fun getItemViewType(position: Int): Int {
        val forum=forums.get(position)
        if (forum.Image=="noimage"){
            return VIEW_TYPE_TEXT
        }else{
            return VIEW_TYPE_IMAGE
        }
    }

    private val recyclerListDiffer = AsyncListDiffer(this, diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForumHolder {
        if (viewType==VIEW_TYPE_IMAGE){
            val view= LayoutInflater.from(parent.context).inflate(R.layout.forumimagecard,parent,false)
            return ForumHolder(view)
        }else{
            val view= LayoutInflater.from(parent.context).inflate(R.layout.cardviewcontac,parent,false)
            return ForumHolder(view)
        }

    }

    override fun onBindViewHolder(holder: ForumHolder, position: Int) {
        val name=holder.itemView.findViewById<TextView>(R.id.forumName)
        val text=holder.itemView.findViewById<TextView>(R.id.forumText)
        val Profileimage=holder.itemView.findViewById<com.github.siyamed.shapeimageview.CircularImageView>(R.id.forumImage)
        val image=holder.itemView.findViewById<ImageView>(R.id.image)
        if(forums.get(position).Image!="noimage"){
            name.text=forums.get(position).name
            text.text=forums.get(position).text
            Picasso.get().load(forums.get(position).profileImage).into(Profileimage)
            Picasso.get().load(forums.get(position).Image).into(image)
        }else{
            name.text=forums.get(position).name
            text.text=forums.get(position).text
            Picasso.get().load(forums.get(position).profileImage).into(Profileimage)
        }

    }

    override fun getItemCount(): Int {
       return forums.size
    }
}