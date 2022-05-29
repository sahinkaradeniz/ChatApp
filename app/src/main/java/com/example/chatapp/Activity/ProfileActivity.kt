package com.example.chatapp.Activity

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.chatapp.data.User
import com.example.chatapp.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import java.util.jar.Manifest

class ProfileActivity : AppCompatActivity() {
    private lateinit var  firebaseAuth: FirebaseAuth
    private lateinit var binding:ActivityProfileBinding
    var selectedBitmap:Bitmap?=null
    var selectedImage:Uri?=null
    var profileImage:String?=null
    val storage=Firebase.storage
    val user= User()
    val db=Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()
        val name=intent.getStringExtra("name")
        val phone=intent.getStringExtra("phone")
        val imageUrl=intent.getStringExtra("image")
        binding.run {
            userName.setText(name)
            profilePhone.setText(phone)
            Picasso.get().load(firebaseAuth.currentUser!!.photoUrl).into(binding.profilePhoto)
        }

        binding.profilePhoto.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
                //izin verilmemiş istememiz gerekiyor
                ActivityCompat.requestPermissions(this, arrayOf(READ_EXTERNAL_STORAGE),1)
            }else{
                val storageIntent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(storageIntent,2)
            }
        }

        binding.updateImage.setOnClickListener {

            if (selectedImage!=null){
                val reference=storage.reference
                val imageReference=reference.child("images").child(firebaseAuth.currentUser!!.uid)
                imageReference.putFile(selectedImage!!).addOnSuccessListener { task ->
                        val upload =reference.child("images").child(firebaseAuth.currentUser!!.uid) // yüklenilen görselin referansı
                        upload.downloadUrl.addOnSuccessListener { uri ->
                            val downlondURl=uri// URL dowland ediyoruz
                            println(downlondURl)
                            setUserProfileUrl(downlondURl)
                        }.addOnFailureListener { e ->
                            Toast.makeText(applicationContext,e.localizedMessage,Toast.LENGTH_LONG).show()
                        }
                        Toast.makeText(this,"Upload image ",Toast.LENGTH_LONG).show()
                }.addOnFailureListener{ exception ->
                    Toast.makeText(applicationContext,exception.localizedMessage,Toast.LENGTH_LONG).show()

                }

            }

        }
        binding.updateIdea.setOnClickListener {
            val idea=binding.ideaText.text.toString()
            db.collection("users").document(firebaseAuth.currentUser!!.uid)
                .update(mapOf(
                    "message" to idea
                ))
        }


        binding.backButton.setOnClickListener {
            val intent= Intent(this,ChatActivity::class.java)
            startActivity(intent)
        }
        binding.button.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }

        }
    private fun checkUser(){
        val firebaseUser=firebaseAuth.currentUser
       if(firebaseUser==null){
           //logged out
           startActivity(Intent(this,MainActivity::class.java))
           finish()
       }
    }

    override fun onRequestPermissionsResult( //izinler sonucunda reddedilme veya kabul edilme durumlarında ne yapmamız gerekiyor
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode==1){
            if (grantResults.size>0&& grantResults[0]==PackageManager.PERMISSION_GRANTED){
                //izin verilmiş
                val storageIntent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(storageIntent,2)
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==2&&resultCode== RESULT_OK&&data!=null) {
        // bir data seçti
            selectedImage= data.data
            binding.updateImage.visibility= View.VISIBLE // update button görünürlüğü
            if (selectedImage!=null){

                if (Build.VERSION.SDK_INT>=28){
                        val source=ImageDecoder.createSource(this.contentResolver,selectedImage!!)
                    selectedBitmap=ImageDecoder.decodeBitmap(source)
                    binding.profilePhoto.setImageBitmap(selectedBitmap)
                }else{
                    selectedBitmap=MediaStore.Images.Media.getBitmap(this.contentResolver,selectedImage)
                    binding.profilePhoto.setImageBitmap(selectedBitmap)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun setUserProfileUrl(url:Uri){
            val user =firebaseAuth.currentUser
        val profileUpdates= userProfileChangeRequest {
            photoUri=url
        }
        user!!.updateProfile(profileUpdates).addOnCompleteListener { task ->
            if(task.isSuccessful){
                db.collection("users").document(firebaseAuth.currentUser!!.uid)
                    .update(mapOf(
                        "profileImage" to firebaseAuth.currentUser!!.photoUrl,
                    ))
              println("update : "+url.toString())
            }
        }

    }
    }
