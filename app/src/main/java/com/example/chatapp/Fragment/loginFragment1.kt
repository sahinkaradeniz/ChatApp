package com.example.chatapp.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chatapp.databinding.FragmentLogin1Binding


import android.content.Intent
import com.example.chatapp.Activity.ChatActivity
import com.example.chatapp.Activity.MessagingActivity
import com.google.firebase.auth.FirebaseAuth


class loginFragment1 :Fragment() {
    private var fragmentLogin1Binding:FragmentLogin1Binding?=null
    private val binding get() = fragmentLogin1Binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
     fragmentLogin1Binding= FragmentLogin1Binding.inflate(inflater,container,false)
        binding.loginButton.setOnClickListener{
        val newIntent=Intent(activity, ChatActivity::class.java)
            startActivity(newIntent)
        }
        binding.imageViewGoogle.setOnClickListener{
            val newIntent=Intent(activity,MessagingActivity::class.java)
            startActivity(newIntent)
        }
        return binding.root
    }
}