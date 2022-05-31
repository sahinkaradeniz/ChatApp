package com.example.chatapp.data

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
 class User:Serializable{
    var id:String?=""
    var name:String?=""
    var phoneNumber:String?=""
    var profileImage:Any?="https://firebasestorage.googleapis.com/v0/b/chatapp-87fa1.appspot.com/o/images%2Fprofileavatar.png?alt=media&token=199a52a3-de81-4e08-8fc5-519eb288f900"
    var message:String?="idea"

   constructor(){}
    constructor(name:String,phoneNumber:String?){
        this.name=name
        this.phoneNumber=phoneNumber
    }
   constructor(id:String?,name:String,phoneNumber:String?,profileImage:Any?,message:String){
       this.id=id
       this.name=name
       this.phoneNumber=phoneNumber
       this.profileImage=profileImage
       this.message=message

   }


}