package com.example.wallpaperapp.registerscreen.model

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

data class PersonModel(
    val name: String,
    val dateOfBirth: String,
    val phoneNumber: String
)

interface DataSourceRepository{
    fun addUserData(userId: String, name: String, dateOfBirth: String, phoneNumber: String)
}

class DataSourceRepositoryImpl: DataSourceRepository{
    private val _db = Firebase.firestore

    override fun addUserData(userId: String, name: String, dateOfBirth: String, phoneNumber: String){
        val personMap = hashMapOf(
            "name" to name,
            "dateOfBirth" to dateOfBirth,
            "phoneNumber" to phoneNumber
        )
        _db.collection("users").document(userId).set(personMap)
            .addOnCompleteListener {task ->
                if(task.isSuccessful){
                    println("User data successfully saved for UID: $userId")
                }
            }
            .addOnFailureListener{ e ->
                println("Error saving user data: ${e.message}")
            }
    }
}