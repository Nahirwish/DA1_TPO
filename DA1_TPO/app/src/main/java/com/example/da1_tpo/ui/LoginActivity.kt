package com.example.da1_tpo.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.VideoView
import com.example.da1_tpo.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.ktx.Firebase


class LoginActivity : AppCompatActivity() {
    private lateinit var btn_login: Button
    private lateinit var editTextEmailAddress: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var googleSignInCliente: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login3)

        // VIDEO
        videoView = findViewById(R.id.videoView2)
        val videoPath = "android.resource://" + packageName + "/" + R.raw.VideoLogin
        val videoUri = Uri.parse(videoPath)
        videoView.setVideoURI(videoUri)
        videoView.start()
        // -----

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInCliente = GoogleSignIn.getClient(this, googleSignInOptions)
        firebaseAuth = FirebaseAuth.getInstance()
        btn_login = findViewById(R.id.btn_login)
        editTextEmailAddress = findViewById(R.id.editTextEmailAddress)
        editTextPassword = findViewById(R.id.editTextPassword)


        btn_login.setOnClickListener {
            val email = editTextEmailAddress.text.toString()
            val pass = editTextPassword.text.toString()

            if(email.isEmpty() || pass.isEmpty()){
                Toast.makeText(this, "Ingresar datos", Toast.LENGTH_SHORT).show()

            }

            signInWithEmail(email, pass)

        }

    }

    private fun getGoogleSignInOptions(): GoogleSignInOptions {
        return GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
    }

    private fun signInWithEmail(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                val firebaseUser = firebaseAuth.currentUser
                val isNewUser = authResult.additionalUserInfo?.isNewUser ?: false

                if (isNewUser) {
                    Toast.makeText(this@LoginActivity, "Cuenta creada", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@LoginActivity, "Cuenta existente", Toast.LENGTH_LONG).show()
                }

                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this@LoginActivity, "Login Fallido: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 100) {
            val accountTask = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = accountTask.getResult(ApiException::class.java)
                firebaseAuthWithGoogleAccount(account)
            } catch (e: Exception) {
                Log.d("Demo", "onActivityResult: ${e.message}")
            }
        }
    }

    private fun firebaseAuthWithGoogleAccount(account: GoogleSignInAccount?){
        val credential = GoogleAuthProvider.getCredential(account!!.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener { authResult ->
                val firebaseUser = firebaseAuth.currentUser
                val uId = firebaseAuth!!.uid
                val email = firebaseUser!!.email

                if(authResult.additionalUserInfo!!.isNewUser){
                    Toast.makeText(this@LoginActivity, "Cuenta creada", Toast.LENGTH_LONG).show()
                }
                else{
                    Toast.makeText(this@LoginActivity, "Cuenta existente", Toast.LENGTH_LONG).show()
                }

                startActivity(Intent(this@LoginActivity, MainActivity:: class.java))
                finish()

            }
            .addOnFailureListener{e ->
                Toast.makeText(this@LoginActivity, "Login Fallido", Toast.LENGTH_LONG).show()

            }




    }

}
