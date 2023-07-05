package com.example.da1_tpo.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.da1_tpo.R
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ArtistAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    //private val splash: SplashActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAuth = FirebaseAuth.getInstance()
        bindView()
        bindViewModel()
    }

    override fun onStart() {
        super.onStart()
        //splash.start("Cargando...")
        viewModel.onStart(this)
    }

    private fun bindView() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = ArtistAdapter()
        recyclerView.adapter = adapter
    }

    private fun bindViewModel() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.artists.observe(this) {
            // Actualizar la lista de la pantalla
            adapter.Update(it)

        }
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser == null) {
            // Usuario no logueado
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }


}


/*protected lateinit var  mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login3)

    }*/