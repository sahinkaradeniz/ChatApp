package com.example.chatapp.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.chatapp.Fragment.FragmentChatt
import com.example.chatapp.Fragment.FragmentPeople
import com.example.chatapp.databinding.ActivityChatBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth


class ChatActivity : AppCompatActivity() {
    private lateinit var binding:ActivityChatBinding
    private val fragmentlist=ArrayList<Fragment>()
    private val fragmentHeadList=ArrayList<String>()

    private lateinit var auth: FirebaseAuth
    private lateinit var  firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()
        checkUser()
        binding.userImage.setOnClickListener {
            val intent=Intent(this,ProfileActivity::class.java)
            val name=firebaseAuth.currentUser!!.displayName
            val phone=firebaseAuth.currentUser!!.phoneNumber
            val image=firebaseAuth.currentUser!!.photoUrl
            intent.putExtra("name",name)
            intent.putExtra("phone",phone)
            intent.putExtra("image",image)
            startActivity(intent)
        }
        fragmentlist.add(FragmentChatt())
        fragmentlist.add(FragmentPeople())

        fragmentHeadList.add("Chats")
        fragmentHeadList.add("People")

        val adapter= ViewPagerAdapter(this)
        binding.viewpager.adapter=adapter

        TabLayoutMediator(binding.tablayout,binding.viewpager){tab,position -> //Tab layout kısımları içerisinde index numarasına göre veri yazacak
            tab.setText(fragmentHeadList[position])
        }.attach()



    }
    inner class  ViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity){ //görüntüleme işlemini gerçekleştiriyor
        override fun getItemCount(): Int {
            return fragmentlist.size //kaç tane fragment organize ediceğinin sayısını veriyoruz
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentlist[position]
        }

    }
    private fun checkUser(){
        val firebaseUser=firebaseAuth.currentUser
        if(firebaseUser==null){
            //  logged out
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}