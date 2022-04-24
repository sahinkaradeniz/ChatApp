package com.example.chatapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.databinding.DataBindingUtil
import com.example.chatapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private val fragmentlist=ArrayList<Fragment>()
    private val fragmentHeadList=ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)

        fragmentlist.add(loginFragment1())
        fragmentlist.add(loginFragment2())

        fragmentHeadList.add("Giriş Yap")
        fragmentHeadList.add("Kayit Ol")

        val adapter=ViewPagerAdapter(this)
        binding.viewpager.adapter=adapter

        TabLayoutMediator(binding.tablayout,binding.viewpager){tab,position -> //Tab layout kısımları içerisinde index numarasına göre veri yazacak
            tab.setText(fragmentHeadList[position])
        }.attach()


    }
    inner class  ViewPagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity){ //görüntüleme işlemini gerçekleştiriyor
        override fun getItemCount(): Int {
          return fragmentlist.size //kaç tane fragment organize ediceğinin sayısını veriyoruz
        }

        override fun createFragment(position: Int): Fragment {
           return fragmentlist[position]
        }

    }
}