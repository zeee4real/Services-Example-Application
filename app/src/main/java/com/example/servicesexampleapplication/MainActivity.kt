package com.example.servicesexampleapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "ServiceDemo"
    }

    private lateinit var serviceIntent: Intent
    private var myService: MyService? = null
    private var isServiceBound: Boolean = false
    private var serviceConnection: ServiceConnection? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.i(TAG, "MainActivity thread " + Thread.currentThread().id)

        serviceIntent = Intent(this, MyService::class.java)
        startServiceBtn.setOnClickListener {
            Log.i(TAG, "Service started on thread " + Thread.currentThread().id)
            startService(serviceIntent)
        }

        stopServiceBtn.setOnClickListener {
            stopService(serviceIntent)
        }

        bindServiceBtn.setOnClickListener {
            bindService()
        }

        unbindServiceBtn.setOnClickListener {
            unbindService()
        }

        getRandomNumberBtn.setOnClickListener {
            setRandomNumber()
        }
    }

    private fun bindService() {
        if (serviceConnection == null) {
            serviceConnection = object : ServiceConnection {
                override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) {
                    val myServiceBinder: MyService.MyServiceBinder? =
                        iBinder?.let { it as MyService.MyServiceBinder }
                    myService = myServiceBinder?.service
                    isServiceBound = true
                }

                override fun onServiceDisconnected(p0: ComponentName?) {
                    isServiceBound = false
                }

            }
        }
        //creates the service if it is not yet created when the service tries to bind
        bindService(serviceIntent, serviceConnection!!, Context.BIND_AUTO_CREATE)
    }

    private fun unbindService() {
        if(isServiceBound){
            unbindService(serviceConnection!!)
            isServiceBound = false
        }
    }

    private fun setRandomNumber() {
        if(isServiceBound)
            randomNumberTV.text = "Random Number: ${myService?.getRandomNumber()}"
        else
            randomNumberTV.text = "Service Not Bound"
    }
}