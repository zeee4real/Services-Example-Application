package com.example.servicesexampleapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG ="ServiceDemo"
    }

    private lateinit var serviceIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i(TAG, "MainActivity thread " +Thread.currentThread().id)

        serviceIntent = Intent(this, MyService::class.java)
        startServiceBtn.setOnClickListener {
            startService(serviceIntent)
        }
    }
}