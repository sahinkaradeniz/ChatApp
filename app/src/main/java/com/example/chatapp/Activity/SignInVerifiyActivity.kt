package com.example.chatapp.Activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.chatapp.databinding.ActivityVerifiyBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class VerifiyActivity : AppCompatActivity() {
    private lateinit var binding:ActivityVerifiyBinding
    private lateinit var phone:String
    private var forceResendingToken: PhoneAuthProvider.ForceResendingToken?=null
    private  var mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks?=null
    private var mVerificationId:String?=null
    private lateinit var firebaseAuth: FirebaseAuth
    private val TAG ="MAIN_TAG"
    private lateinit var progessDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        binding= ActivityVerifiyBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        phone =intent.getStringExtra("phone").toString()
        setContentView(binding.root)
        firebaseAuth= FirebaseAuth.getInstance()
        progessDialog= ProgressDialog(this)
        progessDialog.setTitle("Please Wait")
        progessDialog.setCanceledOnTouchOutside(false)
        binding.phoneText.setText("Please enter code to : $phone")
        mCallBack= object :PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential)
                Log.d(TAG,"onVerificationCompleted")

            }

            override fun onVerificationFailed(e: FirebaseException) {
                progessDialog.dismiss()
                Toast.makeText(this@VerifiyActivity,"${e.message}", Toast.LENGTH_SHORT).show()
                Log.d(TAG,"onVerificationFailed : ${e.message}")

            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {

                Log.d(TAG,"onCodeSent: $verificationId")
                mVerificationId=verificationId
                forceResendingToken=token
                progessDialog.dismiss()

                Toast.makeText(this@VerifiyActivity,"Verification code sent..", Toast.LENGTH_SHORT).show()

            }


        }
        startPhoneNumberVerification(phone)
        binding.login.setOnClickListener {

            val code =binding.verifyTextLogin.text.toString().trim()
            if(TextUtils.isEmpty(code)){
                Toast.makeText(this@VerifiyActivity,"Please enter verification code ", Toast.LENGTH_SHORT).show()
            }else{
                VerifyPhoneNumberWithCode(mVerificationId,code)
            }
        }
        binding.resendText.setOnClickListener {
            resendVerificationCode(phone,forceResendingToken)
        }
    }

   private fun startPhoneNumberVerification(phone:String){
        Log.d(TAG,"startPhoneNumberVerification : $phone")
        progessDialog.setMessage("Verifying phone Number...")
        progessDialog.show()
        val options= PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(mCallBack!!)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    private fun resendVerificationCode(phone: String,token: PhoneAuthProvider.ForceResendingToken?){
        progessDialog.setMessage("Resending Code...")
        progessDialog.show()
        Log.d(TAG,"resendVerificationCode : $phone")
        val options= PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phone)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(mCallBack!!)
            .setForceResendingToken(token!!)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
    private fun VerifyPhoneNumberWithCode(verificationId:String?,code:String){
        progessDialog.setMessage("Verifying Code..")
        progessDialog.show()
        Log.d(TAG,"startPhoneNumberVerification : $verificationId $code")
        val credential=PhoneAuthProvider.getCredential(verificationId.toString(),code)
        signInWithPhoneAuthCredential(credential)
    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        Log.d(TAG,"signInWithPhoneAuthCredential : $credential")
        progessDialog.setMessage("Loging Inn..")
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                progessDialog.dismiss()
                val u=firebaseAuth.currentUser!!
                if(u.displayName==null){
                    Toast.makeText(this,"Create New Profile",Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,MainActivity::class.java))
                }else{
                    val pho  = firebaseAuth.currentUser!!.phoneNumber
                    Toast.makeText(this,"logged ın as $pho", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,ChatActivity::class.java))
                    finish()
                }
            }
            .addOnFailureListener { e ->
                //login failed
                progessDialog.dismiss()
                Toast.makeText(this,"${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

}