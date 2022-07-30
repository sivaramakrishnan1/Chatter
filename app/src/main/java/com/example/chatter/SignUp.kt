package com.example.chatter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class SignUp : AppCompatActivity() {

    private lateinit var inUsername : EditText
    private lateinit var inEmail : EditText
    private lateinit var inPassword : EditText
    private lateinit var incPassword : EditText
    private lateinit var btnSignUp : Button
    private lateinit var btnCancel : Button
    private lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        mAuth = FirebaseAuth.getInstance()
        inUsername = findViewById(R.id.in_username)
        inEmail = findViewById(R.id.in_email)
        inPassword = findViewById(R.id.in_password)
        incPassword = findViewById(R.id.in_cPassword)
        btnSignUp = findViewById(R.id.btn_signUp)
        btnCancel = findViewById(R.id.btn_cancel)

        btnSignUp.setOnClickListener{
            val name = inUsername.text.toString()
            val email = inEmail.text.toString()
            val password = inPassword.text.toString()
            val cPassword = incPassword.text.toString()


            if(password == cPassword)
            {
                signUp(name, email, password)
                // the code for function call signUp() should come here
            }
            else
            {
                Toast.makeText(this@SignUp, "Password mismatch!", Toast.LENGTH_SHORT).show()
            }
        }
        btnCancel.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }


    private fun signUp(name:String, email:String, password:String)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task->
                if(task.isSuccessful)
                {
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                }
                else
                {
                    Toast.makeText(this@SignUp, "Some-Error-Occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }
    

    private fun addUserToDatabase(name: String, email: String, uid: String)
    {
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name, email, uid))
    }
}