package com.example.chatapp.Activity

import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.chatapp.data.Forum
import com.example.chatapp.databinding.ActivityForumEditBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso


class ForumEdit : AppCompatActivity() {
    private lateinit var  firebaseAuth: FirebaseAuth
    private lateinit var binding:ActivityForumEditBinding
    var selectedBitmap: Bitmap?=null
    var selectedImage: Uri?=null
    val storage= Firebase.storage
    val db= Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityForumEditBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()
        val name=intent.getStringExtra("name")

        binding.run{
            binding.editName.setText(name)
            Picasso.get().load(firebaseAuth.currentUser!!.photoUrl).into(binding.EditImage)
        }

        binding.editButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                //izin verilmemiş istememiz gerekiyor
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
            }else{
                val storageIntent= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(storageIntent,2)
            }
        }
        binding.Send.setOnClickListener{
            if (selectedImage!=null){
                val reference=storage.reference
                val imageReference=reference.child("images").child(firebaseAuth.currentUser!!.uid)

                imageReference.putFile(selectedImage!!).addOnSuccessListener { task ->
                    //url Alınacak
                    val upload =reference.child("images").child(firebaseAuth.currentUser!!.uid) // yüklenilen görselin referansı
                    upload.downloadUrl.addOnSuccessListener { uri ->
                        val downlondURl=uri// URL dowland ediyoruz
                        println(downlondURl)
                        sendImagePost(downlondURl.toString())
                    }.addOnFailureListener { e ->
                        Toast.makeText(applicationContext,e.localizedMessage, Toast.LENGTH_LONG).show()
                    }
                    Toast.makeText(this,"Upload image ", Toast.LENGTH_LONG).show()
                }.addOnFailureListener{ exception ->
                    Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_LONG).show()

                }

            }else{
                sendImagePost(null)
            }
            val intent=Intent(this,ChatActivity::class.java)
            startActivity(intent)
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
            binding.imageEdit.visibility= View.VISIBLE
            if (selectedImage!=null){

                if (Build.VERSION.SDK_INT>=28){
                    val source= ImageDecoder.createSource(this.contentResolver,selectedImage!!)
                    selectedBitmap= ImageDecoder.decodeBitmap(source)
                    binding.imageEdit.setImageBitmap(selectedBitmap)
                }else{
                    selectedBitmap=MediaStore.Images.Media.getBitmap(this.contentResolver,selectedImage)
                    binding.imageEdit.setImageBitmap(selectedBitmap)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun sendImagePost(url:String?){
            val id=firebaseAuth.currentUser!!.uid
            val name= firebaseAuth.currentUser!!.displayName.toString()
            val text=binding.editText.text.toString()
            val profileImage=firebaseAuth.currentUser!!.photoUrl.toString()
            val date=Timestamp.now()
            val dates=Forum(name,id,profileImage,text, date.toString())
            if(url !=null){
                dates.Image=url
            }
            db.collection("forum")
                .add(dates)
                .addOnSuccessListener { documentReference ->
                    Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference}")
                }
                .addOnFailureListener { e ->
                    Log.w(ContentValues.TAG, "Error adding document", e)
                }

    }
}