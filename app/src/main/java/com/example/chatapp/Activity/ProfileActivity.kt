package com.example.chatapp.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chatapp.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    private lateinit var  firebaseAuth: FirebaseAuth
    private lateinit var binding:ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()
        val name=intent.getStringExtra("name")
        val phone=intent.getStringExtra("phone")
        binding.userName.setText(name)
        binding.profilePhone.setText(phone)
        binding.backButton.setOnClickListener {
            val intent= Intent(this,ChatActivity::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

        }
    private fun checkUser(){
        val firebaseUser=firebaseAuth.currentUser
       if(firebaseUser==null){
           //logged out
           startActivity(Intent(this,MainActivity::class.java))
           finish()
       }
    }
    }
