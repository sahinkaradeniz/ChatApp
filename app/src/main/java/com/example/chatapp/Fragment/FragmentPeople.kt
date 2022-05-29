package com.example.chatapp.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp.Adapters.ContacRvAdapter
import com.example.chatapp.R
import com.example.chatapp.data.ContactData
import com.example.chatapp.databinding.FragmentPeopleBinding

class FragmentPeople : Fragment() {
    private var _binding:FragmentPeopleBinding?=null
    private val binding get()=_binding!!
    private lateinit var contactData:ArrayList<ContactData>
    private lateinit var adapter: ContacRvAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        _binding= FragmentPeopleBinding.inflate(inflater,container,false)
        binding.recyclerviewcontac.setHasFixedSize(true)
        binding.recyclerviewcontac.layoutManager=LinearLayoutManager(activity)

        val c1=ContactData("Sahin","Meşgul",R.drawable.human)
        val c2=ContactData("Nisa","Karnı aç",R.drawable.test_avatar)
        val c3=ContactData("Fako","Uyuyor",R.drawable.human2)

        var contacList=ArrayList<ContactData>()
        contacList.add(c1)
        contacList.add(c2)
        contacList.add(c3)
        contacList.add(c1)
        contacList.add(c2)
        contacList.add(c3)
        contacList.add(c1)
        contacList.add(c2)
        contacList.add(c3)

        binding.recyclerviewcontac.adapter=ContacRvAdapter(contacList)

        return binding.root
    }

}