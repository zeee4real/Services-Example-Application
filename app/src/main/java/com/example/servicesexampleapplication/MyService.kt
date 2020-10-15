package com.example.servicesexampleapplication

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.example.servicesexampleapplication.MainActivity.Companion.TAG
import java.util.*

class MyService : Service() {

    private var randomNumber: Int = 0
    private var isRandomNumberGeneratorOn: Boolean = false

    private val min = 0
    private val max = 100

    inner class MyServiceBinder : Binder() {
        val service: MyService
            get() = this@MyService
    }

    private val binder: IBinder = MyServiceBinder()

    override fun onBind(p0: Intent?): IBinder? {
        Log.i(TAG, "In onBind^ " + Thread.currentThread().id)
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i(TAG, "In onStartCommand Thread id " + Thread.currentThread().id)
        isRandomNumberGeneratorOn = true
        Thread {
            startRandomNumberGenerator()
        }.start()
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopRandomNumberGenerator()
        Log.i(TAG, "Service Destroyed")
    }

    private fun startRandomNumberGenerator() {
        while (isRandomNumberGeneratorOn) {
            try {
                Thread.sleep(1000)
                if (isRandomNumberGeneratorOn) {
                    randomNumber = Random().nextInt(max) + min;
                    Log.i(
                        TAG,
                        "Thread id: " + Thread.currentThread().id + ", Random Number: " + randomNumber
                    )
                }
            } catch (i: InterruptedException) {
                Log.i(TAG, "Thread Interrupted")
            }
        }
    }

    private fun stopRandomNumberGenerator() {
        isRandomNumberGeneratorOn = false
    }

    fun getRandomNumber(): Int = randomNumber

    override fun onUnbind(intent: Intent?): Boolean {
        Log.i(TAG, "In onUnbind")
        return super.onUnbind(intent)
    }

}