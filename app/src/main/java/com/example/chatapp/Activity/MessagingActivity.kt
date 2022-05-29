package com.example.chatapp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.chatapp.databinding.ActivityMessagingBinding
import com.squareup.picasso.Picasso


class MessagingActivity : AppCompatActivity() {
        private lateinit var binding: ActivityMessagingBinding
        private lateinit var id:String
        private lateinit var name:String
        private lateinit var message:String
        private lateinit var phoneNumber:String
        private lateinit var profileImage:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMessagingBinding.inflate(layoutInflater)
        val view=binding.root
        id= intent.getStringExtra("id").toString()
        name= intent.getStringExtra("name").toString()
        message= intent.getStringExtra("message").toString()
        phoneNumber= intent.getStringExtra("phoneNumber").toString()
        profileImage= intent.getStringExtra("profileImage").toString()

        binding.userName.setText(name)

        Picasso.get().load(profileImage).into(binding.userImage)
        setContentView(view)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        binding.backMenu.setOnClickListener {
            val intent=Intent(this,ChatActivity::class.java)
            startActivity(intent)
        }
    }
}