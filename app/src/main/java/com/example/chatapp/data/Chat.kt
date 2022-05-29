package com.example.chatapp.data

data class Chat(
    val name:String="",
    val id:String="",
    val phoneNumber:String?="",
    val profileImage: String ="https://firebasestorage.googleapis.com/v0/b/chatapp-87fa1.appspot.com/o/images%2Fprofileavatar.png?alt=media&token=199a52a3-de81-4e08-8fc5-519eb288f900",
    val message:String?=null){
}