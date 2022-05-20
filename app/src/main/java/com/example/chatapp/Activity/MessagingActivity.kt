package com.example.chatapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatapp.databinding.ActivityChatingBinding
import com.example.chatapp.databinding.ActivityMessagingBinding


class MessagingActivity : AppCompatActivity() {
        private lateinit var binding: ActivityMessagingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMessagingBinding.inflate(layoutInflater)
        val view=binding.root
      val name=  intent.getStringExtra("name")
        binding.textView9.setText(name.toString())
        setContentView(view)


    }
}