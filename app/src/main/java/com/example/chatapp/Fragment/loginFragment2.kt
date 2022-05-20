package com.example.chatapp.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.Fragment
import com.example.chatapp.databinding.FragmentLogin2Binding
import com.google.firebase.database.FirebaseDatabase
import com.example.chatapp.data.User

class loginFragment2 :Fragment() {
    private var _binding:FragmentLogin2Binding?=null
    private val binding get()=_binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding= FragmentLogin2Binding.inflate(inflater,container,false)
        val database=FirebaseDatabase.getInstance().reference
        binding.buttonSign.setOnClickListener{
            var name=binding.signName.text.toString()
            var sname=binding.signSname.text.toString()
            var uname=binding.signUname.text.toString()
            var email=binding.signemail.text.toString()
            var password=binding.signPassword.text.toString()
            database.setValue(User(name,sname,uname,email,password))
            Toast.makeText(activity,"Successfully Completed !",Toast.LENGTH_SHORT).show()
            Clear()
        }
        return binding.root
    }
    public fun Clear(){
        binding.signName.text.clear()
        binding.signSname.text.clear()
        binding.signUname.text.clear()
        binding.signemail.text.clear()
        binding.signPassword.text.clear()

    }
}