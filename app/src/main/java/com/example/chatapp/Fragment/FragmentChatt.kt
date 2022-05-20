package com.example.chatapp.Fragment
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.Activity.Chating
import com.example.chatapp.Activity.MessagingActivity
import com.example.chatapp.Activity.OnChatClickListener
import com.example.chatapp.Adapters.TalksAdapter
import com.example.chatapp.R
import com.example.chatapp.data.TalksData
import com.example.chatapp.databinding.FragmentChattBinding


@Suppress("UNREACHABLE_CODE")
class FragmentChatt : Fragment() ,OnChatClickListener{
    private var _binding:FragmentChattBinding?=null
    private val binding get()=_binding!!
    private lateinit var talksData: ArrayList<TalksData>
    private lateinit var adapter: TalksAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding= FragmentChattBinding.inflate(inflater,container,false)

        binding.recyclerview.setHasFixedSize(true)//tasarıma oturması icin
        binding.recyclerview.layoutManager=LinearLayoutManager(activity)

        val t=TalksData("Hello ı am Sahin","Şahin Karadeniz","13:55", R.drawable.human)
        val t2=TalksData("Hello ı am niss","Nisa Karadeniz","13:56",R.drawable.human3)
        val t3=TalksData("Hello ı am Cat","Minik ","14:55",R.drawable.human2)
        val t4=TalksData("Hello ı am Havva","Havva Coşkun","14:55",R.drawable.human)
        val t5=TalksData("Hello ı am Sahin","Şahin Karadeniz","13:55", R.drawable.human)
        val t6=TalksData("Hello ı am niss ","Nisa Karadeniz","13:56",R.drawable.human3)
        val t7=TalksData("Hello ı am Cat","Minik ","14:55",R.drawable.human2)
        val t8=TalksData("Hello ı am Havva","Havva Coşkun","14:55",R.drawable.human)
        val t9=TalksData("Hello ı am Sahin","Şahin Karadeniz","13:55", R.drawable.human)
        val t10=TalksData("Hello ı am niss ","Nisa Karadeniz","13:56",R.drawable.human3)
        val t11=TalksData("Hello ı am Cat","Minik ","14:55",R.drawable.human2)
        val t12=TalksData("Hello ı am Havva","Havva Coşkun","14:55",R.drawable.human)


        var talkList=ArrayList<TalksData>()
        talkList.add(t)
        talkList.add(t2)
        talkList.add(t3)
        talkList.add(t4)
        talkList.add(t)
        talkList.add(t2)
        talkList.add(t3)
        talkList.add(t4)
        talkList.add(t)
        talkList.add(t2)
        talkList.add(t3)
        talkList.add(t4)

        binding.recyclerview.adapter=TalksAdapter(talkList,this)

        return binding.root

    }

    override fun onChatItem(talksData: TalksData) {
        val intent=Intent(activity,MessagingActivity::class.java)
         intent.putExtra("name",talksData.name)
        startActivity(intent)
        Toast.makeText(context,"Name : "+talksData.name+"",Toast.LENGTH_LONG).show()
    }

}