package com.example.chatapp.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatapp.data.TalksData
import com.example.chatapp.databinding.ActivityChatingBinding
import com.example.chatapp.databinding.ActivityMessagingBinding


class MessagingActivity : AppCompatActivity() {
        private lateinit var binding: ActivityMessagingBinding
        private lateinit var data :TalksData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMessagingBinding.inflate(layoutInflater)
        val view=binding.root
       data=  intent.getSerializableExtra("name") as TalksData
        binding.textView9.setText(data.name.toString())
        //TalksimageView.setImageResource(newTalksData.image)
        binding.imageView.setImageResource(data.image)
        setContentView(view)


    }
}