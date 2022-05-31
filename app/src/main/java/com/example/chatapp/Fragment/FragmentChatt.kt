package com.example.chatapp.Fragment
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.Activity.MessagingActivity
import com.example.chatapp.Adapters.UsersRecyclerAdapter
import com.example.chatapp.Listener.OnChatClickListener
import com.example.chatapp.data.Chat
import com.example.chatapp.databinding.FragmentChattBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query



@Suppress("UNREACHABLE_CODE")
class FragmentChatt : Fragment() ,OnChatClickListener{
    private var _binding:FragmentChattBinding?=null
    private val binding get()=_binding!!
    private lateinit var adapter: UsersRecyclerAdapter
    private lateinit var firestore : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private var users= arrayListOf<Chat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentChattBinding.inflate(inflater,container,false)
        return binding.root

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= UsersRecyclerAdapter(this)
        binding.recyclerview.adapter=adapter
        binding.recyclerview.setHasFixedSize(true)//tasarıma oturması icin
        binding.recyclerview.layoutManager=LinearLayoutManager(requireContext())


        firestore.collection("users").orderBy("name", Query.Direction.ASCENDING).addSnapshotListener { value, error ->
            if(value!=null){
                if (value.isEmpty){
                    Toast.makeText(requireContext(),"Users null",Toast.LENGTH_LONG).show()
                }else{
                    val documents=value.documents
                    users.clear()
                    for (document in documents){
                        val id=document.get("id") as String
                        val name=document.get("name") as String
                        val image=document.get("profileImage") as String
                        val message=document.get("message") as String
                        val phone=document.get("phoneNumber") as String
                        val pho=phone.toString()
                        val chat=Chat(name,id,pho,image,message)
                        users.add(chat)
                        adapter.users=users

                        println(name)
                    }
                }
            }
            adapter.notifyDataSetChanged()
        }

    }

    override fun onChatItem(chat: Chat) {
        val intent1=Intent(activity, MessagingActivity::class.java)
        intent1.putExtra("id",chat.id)
        intent1.putExtra("message",chat.message)
        intent1.putExtra("name",chat.name)
        intent1.putExtra("phoneNumber",chat.phoneNumber)
        intent1.putExtra("profileImage",chat.profileImage)
        startActivity(intent1)
    }


}




