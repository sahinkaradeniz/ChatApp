package com.example.chatapp.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chatapp.Activity.ChatActivity
import com.example.chatapp.databinding.FragmentLogin1Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class loginFragment1 : Fragment() {
   private var _binding:FragmentLogin1Binding?=null
    private val binding get() = _binding!!
    val db= Firebase.firestore
    var auth: FirebaseAuth?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
     _binding= FragmentLogin1Binding.inflate(inflater,container,false)
        binding.loginButton.setOnClickListener {
            val phone=binding.phoneNumber.toString()
           val intent=Intent(activity,ChatActivity::class.java)
            intent.putExtra("phone",phone)
            startActivity(intent)

        }

        return binding.root

    }
}