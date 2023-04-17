package com.example.closetcompanion.models

data class User(
    val username: String,
    val password: String,
    val first_name: String,
    val last_name: String,
    val email_address: String,
    val dob: String,
    ): java.io.Serializable
