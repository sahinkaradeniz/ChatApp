package com.example.chatapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.chatapp.R
import com.example.chatapp.databinding.ActivityChatingBinding
import com.example.chatapp.databinding.ActivityMessagingBinding

class Chating : AppCompatActivity() {
    private lateinit var binding: ActivityChatingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChatingBinding.inflate(layoutInflater)
        val view=binding.root
       val name= intent.getStringExtra("name")
        binding.chatting.setText(name.toString())

        setContentView(view)
    }
}