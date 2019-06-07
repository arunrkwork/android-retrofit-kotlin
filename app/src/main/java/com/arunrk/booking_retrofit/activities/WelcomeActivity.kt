package com.arunrk.booking_retrofit.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.arunrk.booking_retrofit.R
import com.arunrk.booking_retrofit.helpers.DestinationAdapter
import com.arunrk.booking_retrofit.models.Destination
import com.arunrk.booking_retrofit.models.SampleData
import com.arunrk.booking_retrofit.services.MessageService
import com.arunrk.booking_retrofit.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_destination_list.*
import kotlinx.android.synthetic.main.activity_welcome.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val messageService = ServiceBuilder.buildService(MessageService::class.java)
        val requestCall = messageService.getMessage("http://192.168.1.100:7000/messages")

        requestCall.enqueue(object: Callback<String> {

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    val msg = response.body()
                    msg?.let { message.text = msg }
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {

            }

        })

    }

    fun getStarted(view: View) {
        val intent = Intent(this, DestinationListActivity::class.java)
        startActivity(intent)
        finish()
    }


}
