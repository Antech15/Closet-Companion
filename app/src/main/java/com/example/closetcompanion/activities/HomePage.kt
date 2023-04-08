package com.example.closetcompanion.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.closetcompanion.R

class HomePage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        findViewById<Button>(R.id.login_button).setOnClickListener {
            //Start the landing patch.
        }

        findViewById<Button>(R.id.signup_button).setOnClickListener {
            //Start the sign up activity
        }
    }
}