package com.example.chatapp.Fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.chatapp.Activity.ChatActivity
import com.example.chatapp.Activity.VerifiyActivity
import com.example.chatapp.databinding.FragmentLogin1Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class loginFragment1 : Fragment() {
   private var _binding:FragmentLogin1Binding?=null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
     _binding= FragmentLogin1Binding.inflate(inflater,container,false)
        binding.loginButton.setOnClickListener {
            val phone=binding.phoneNumber.text.toString()
            if(TextUtils.isEmpty(phone)){
                Toast.makeText(activity,"Please enter phone number",Toast.LENGTH_SHORT).show()
            }else {
                val intent=Intent(activity,VerifiyActivity::class.java)
                intent.putExtra("phone",phone)
                startActivity(intent)
            }


        }

        return binding.root

    }
}