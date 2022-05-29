package com.example.chatapp.data

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
 class User:Serializable{
    var id:String?=null
    var name:String?=null
    var phoneNumber:String?=null
    var profileImage:Any?=null
    var message:String?=null

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