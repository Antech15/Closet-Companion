package com.example.closetcompanion.data

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

object FirestoreUtil {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
}