package com.example.chatapp.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.chatapp.data.User
import com.example.chatapp.databinding.ActivityVerifyBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit
class SignUpVerifyActivity : AppCompatActivity() {
    private lateinit var binding:ActivityVerifyBinding
    private lateinit var phone:String
    private lateinit var name: String
    val user= User()
    val db=Firebase.firestore
    private var forceResendingToken:PhoneAuthProvider.ForceResendingToken?=null
    private  var mCallBack:PhoneAuthProvider.OnVerificationStateChangedCallbacks?=null
    private var mVerificationId:String?=null
    private lateinit var firebaseAuth: FirebaseAuth
    private val TAG ="MAIN_TAG"
    private lateinit var progessDialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityVerifyBinding.inflate(layoutInflater)
        val view =binding.root
        phone =intent.getStringExtra("phone").toString()
        name=intent.getStringExtra("name").toString()
        setContentView(view)
        firebaseAuth= FirebaseAuth.getInstance()
        progessDialog= ProgressDialog(this)
        progessDialog.setTitle("Please Wait")
        progessDialog.setCanceledOnTouchOutside(false)
        mCallBack= object :PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential)
                Log.d(TAG,"onVerificationCompleted")

            }

            override fun onVerificationFailed(e: FirebaseException) {
                progessDialog.dismiss()
                Toast.makeText(this@SignUpVerifyActivity,"${e.message}",Toast.LENGTH_SHORT).show()
                Log.d(TAG,"onVerificationFailed : ${e.message}")

            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {

                Log.d(TAG,"onCodeSent: $verificationId")
                mVerificationId=verificationId
                forceResendingToken=token
                progessDialog.dismiss()

                Toast.makeText(this@SignUpVerifyActivity,"Verification code sent..",Toast.LENGTH_SHORT).show()

            }


        }
        startPhoneNumberVerification(phone)
        binding.buttonLogin.setOnClickListener {

            val code =binding.verifyText.text.toString().trim()
            if(TextUtils.isEmpty(code)){
                Toast.makeText(this@SignUpVerifyActivity,"Please enter verification code ",Toast.LENGTH_SHORT).show()
            }else{
                VerifyPhoneNumberWithCode(mVerificationId,code)
            }
        }
        binding.resendCode.setOnClickListener {
            resendVerificationCode(phone,forceResendingToken)
        }

    }
  private fun startPhoneNumberVerification(phone:String){
      Log.d(TAG,"startPhoneNumberVerification : $phone")
       progessDialog.setMessage("Verifying phone Number...")
        progessDialog.show()
        val options=PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phone)
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(mCallBack!!)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    private fun resendVerificationCode(phone: String,token: PhoneAuthProvider.ForceResendingToken?){
        progessDialog.setMessage("Resending Code...")
        progessDialog.show()
        Log.d(TAG,"resendVerificationCode : $phone")
        val options=PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phone)
            .setTimeout(60L,TimeUnit.SECONDS)
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
    private fun addProfile(name: String){
        user.phoneNumber=firebaseAuth.currentUser!!.phoneNumber
        user.id=firebaseAuth.currentUser!!.uid
        user.name=name
        db.collection("users").document(firebaseAuth.currentUser!!.uid)
            .set(user)
            .addOnSuccessListener { documentReference ->
                Log.d(ContentValues.TAG, "DocumentSnapshot added with ID: ${documentReference}")
            }
            .addOnFailureListener { e ->
                Log.w(ContentValues.TAG, "Error adding document", e)
            }
    }
    private fun Profile(name:String){
       val user=Firebase.auth.currentUser
        val profileUpdates= userProfileChangeRequest {
            displayName=name
        }
        user!!.updateProfile(profileUpdates).addOnCompleteListener { task ->
            if(task.isSuccessful){
                Toast.makeText(this,"Profile upload",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        Log.d(TAG,"signInWithPhoneAuthCredential : $credential")
            progessDialog.setMessage("Loging Inn..")
            firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                progessDialog.dismiss()
                val pho  = firebaseAuth.currentUser!!.phoneNumber
                Profile(name)
                addProfile(name)
                Toast.makeText(this,"logged ın as $pho",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,ChatActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                //login failed
                progessDialog.dismiss()
                Toast.makeText(this,"${e.message}",Toast.LENGTH_SHORT).show()
            }
    }

}