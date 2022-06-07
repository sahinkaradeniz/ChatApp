package com.example.chatapp.Activity

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.Adapters.MessageRecyclerAdapter
import com.example.chatapp.data.messageData
import com.example.chatapp.databinding.ActivityMessagingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue.serverTimestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.HashMap

class MessagingActivity : AppCompatActivity() {
        private lateinit var binding:ActivityMessagingBinding
        private lateinit var id:String
        private lateinit var name:String
        private lateinit var message:String
        private lateinit var phoneNumber:String
        private lateinit var profileImage:String
        private lateinit var firestore : FirebaseFirestore
        private lateinit var firebaseAuth: FirebaseAuth
        private lateinit var adapter: MessageRecyclerAdapter

        private var mess= arrayListOf<messageData>()


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMessagingBinding.inflate(layoutInflater)
        val view=binding.root

        firebaseAuth= FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        id= intent.getStringExtra("id").toString()
        name= intent.getStringExtra("name").toString()
        message= intent.getStringExtra("message").toString()
        phoneNumber= intent.getStringExtra("phoneNumber").toString()
        profileImage= intent.getStringExtra("profileImage").toString()
        adapter= MessageRecyclerAdapter(id)
        binding.recyclerView.adapter=adapter
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager=LinearLayoutManager(this)

        val a=firebaseAuth.currentUser!!.phoneNumber
        if (a!! >phoneNumber){
            val k =(a+phoneNumber).toString()
            firestore.collection(k).orderBy("date", Query.Direction.ASCENDING).addSnapshotListener { value, error ->
                if(value!=null){
                    if (value.isEmpty){
                        Toast.makeText(this,"Users null", Toast.LENGTH_LONG).show()
                    }else{
                        val documents=value.documents
                        mess.clear()
                        for (document in documents){
                            val date=document.get("date").toString()
                            val mes=document.get("message") as String
                            val send=document.get("send") as String
                            val to=document.get("to") as String
                            val chat= messageData(mes,send,to,date)
                            mess.add(chat)
                            adapter.messages=mess


                        }
                    }
                }
                adapter.notifyDataSetChanged()
            }
        }else{
            val k =(phoneNumber+a!!).toString()
            firestore.collection(k).orderBy("date", Query.Direction.ASCENDING).addSnapshotListener { value, error ->
                if(value!=null){
                    if (value.isEmpty){
                        Toast.makeText(this,"Users null", Toast.LENGTH_LONG).show()
                    }else{
                        val documents=value.documents
                        mess.clear()
                        for (document in documents){
                            val date=document.get("date").toString()
                            val mes=document.get("message") as String
                            val send=document.get("send") as String
                            val to=document.get("to") as String
                            val chat= messageData(mes,send,to,date)
                            mess.add(chat)
                            adapter.messages=mess


                        }
                    }
                }
                adapter.notifyDataSetChanged()
            }
        }


        binding.userName.setText(name)
        Picasso.get().load(profileImage).into(binding.userImage)
        setContentView(view)
   //     getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        binding.backMenu.setOnClickListener {
            val intent=Intent(this,ChatActivity::class.java)
            startActivity(intent)
        }
        binding.sendButton.setOnClickListener {
            val text=binding.messageText.text.toString()
            sendMessage(text,firebaseAuth.currentUser!!.uid,id)

        }
    }
    private fun sendMessage(message:String,send:String,to:String){

        val dataMap = HashMap<String, Any>()
        dataMap.put("message",message)
        dataMap.put("send",send)
        dataMap.put("to",to)
        dataMap.put("date",serverTimestamp())
        val a=firebaseAuth.currentUser!!.phoneNumber
        if (a!! >phoneNumber){
            val k =(a+phoneNumber).toString()
            firestore.collection(k)
                .add(dataMap)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference}")
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                }
            binding.messageText.text.clear()
        }else{
            val k =(phoneNumber+a!!).toString()
            firestore.collection(k)
                .add(dataMap)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference}")
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                }
            binding.messageText.text.clear()
        }


    }
}

