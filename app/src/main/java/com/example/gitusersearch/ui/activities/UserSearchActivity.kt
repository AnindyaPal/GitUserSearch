package com.example.gitusersearch.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gitusersearch.R

class UserSearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onSearchRequested()
    }
    
}
