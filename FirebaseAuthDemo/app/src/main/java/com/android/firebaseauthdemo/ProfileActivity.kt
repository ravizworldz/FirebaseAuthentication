package com.android.firebaseauthdemo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class ProfileActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var databaseReference :  DatabaseReference? = null
    var database: FirebaseDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        loadProfile()
    }

    private fun loadProfile() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        emailText.text = "Email  -- > "+user?.email

        userreference?.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                firstnameText.text = "Firstname - - > "+snapshot.child("firstname").value.toString()
                lastnameText.text = "Last name - -> "+snapshot.child("lastname").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
            finish()
        }
    }
}