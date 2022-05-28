package com.example.chatapp.Fragment
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.chatapp.Activity.VerifyActivity
import com.example.chatapp.data.User
import com.example.chatapp.databinding.FragmentLogin2Binding
class loginFragment2 :Fragment() {
    private var _binding:FragmentLogin2Binding?=null
    private val binding get()=_binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding= FragmentLogin2Binding.inflate(inflater,container,false)

    //    var user=User(name,phone)
        binding.buttonSign.setOnClickListener{
            val name=binding.signName.text.toString()
            val phone=binding.phoneNumber.text.toString()
            if (TextUtils.isEmpty(phone)){
                Toast.makeText(activity,"Please enter phone number",Toast.LENGTH_SHORT).show()
            }else{
                Clear()
                val intent=Intent(activity,VerifyActivity::class.java)
                intent.putExtra("phone",phone)
                intent.putExtra("name",name)
                startActivity(intent)
            }

        }
        return binding.root
    }
    private fun Clear(){
        binding.signName.text.clear()
        binding.phoneNumber.text.clear()
    }
}