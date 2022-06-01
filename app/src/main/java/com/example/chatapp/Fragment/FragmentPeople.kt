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
import com.example.chatapp.Activity.ForumEdit
import com.example.chatapp.Adapters.ForumRecyclerViewAdapter
import com.example.chatapp.data.Forum
import com.example.chatapp.databinding.FragmentPeopleBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class FragmentPeople : Fragment() {
    private var _binding:FragmentPeopleBinding?=null
    private val binding get()=_binding!!
    private lateinit var adapter:ForumRecyclerViewAdapter
    private lateinit var firestore : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    private var forums= arrayListOf<Forum>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding=FragmentPeopleBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= ForumRecyclerViewAdapter()
        binding.recyclerviewForum.adapter=adapter
        binding.recyclerviewForum.setHasFixedSize(true)
        binding.recyclerviewForum.layoutManager=LinearLayoutManager(requireContext())

        firestore.collection("forum").orderBy("date", Query.Direction.ASCENDING).addSnapshotListener { value, error ->
            if(value!=null){
                if (value.isEmpty){
                    Toast.makeText(requireContext(),"Forms null", Toast.LENGTH_LONG).show()
                }else{
                    val documents=value.documents
                    forums.clear()
                    for (document in documents){
                            val id=document.get("id") as String
                            val name=document.get("name") as String
                            val profileImage=document.get("profileImage") as String
                            val message=document.get("text") as String
                            val image=document.get("image") as String
                            val chat= Forum(name,id,profileImage,message,image)
                            forums.add(chat)
                            adapter.forums=forums
                    }
                }
            }
            adapter.notifyDataSetChanged()
        }

        binding.floatingActionButton.setOnClickListener {
            val intent=Intent(activity, ForumEdit::class.java)
            intent.putExtra("name",FirebaseAuth.getInstance().currentUser!!.displayName)
            startActivity(intent)
        }


    }

}