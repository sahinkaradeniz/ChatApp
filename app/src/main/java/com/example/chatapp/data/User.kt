package com.example.chatapp.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User (var name:String?="",var surname:String?="",var userName:String?="",var email:String?="",var password:String?="",var photo:Int?=0){
}