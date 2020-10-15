package com.example.servicesexampleapplication

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.servicesexampleapplication.MainActivity.Companion.TAG

class MyService: Service() {

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG,"In onStartCommand Thread id "+Thread.currentThread().id)
        stopSelf()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "Service Destroyed")
    }
}