package com.example.chatter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var inEmail : EditText
    private lateinit var inPassword : EditText
    private lateinit var btnSignIn: Button
    private lateinit var btnSignUp: Button
    private lateinit var mAuth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()
        inEmail = findViewById(R.id.in_email)
        inPassword = findViewById(R.id.in_password)
        btnSignIn = findViewById(R.id.btn_signIn)
        btnSignUp = findViewById(R.id.btn_signUp)

        btnSignUp.setOnClickListener{
            val intent = Intent( this, SignUp::class.java)
            startActivity(intent)
        }

        btnSignIn.setOnClickListener{
            val email = inEmail.text.toString()
            val password = inPassword.text.toString()

            signIn(email, password)
        }
    }
    private fun signIn(email:String, password:String) {
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@Login, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@Login, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}