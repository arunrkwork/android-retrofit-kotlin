package com.arunrk.booking_retrofit.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.arunrk.booking_retrofit.R
import com.arunrk.booking_retrofit.models.Destination
import com.arunrk.booking_retrofit.services.DestinationService
import com.arunrk.booking_retrofit.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_destination_create.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationCreateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination_create)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val context = this

        btnCreate.setOnClickListener{
            val destination = Destination()
            destination.city = edCity.text.toString()
            destination.country = edCountry.text.toString()
            destination.description = edDescription.text.toString()
            saveData(context, destination)
        }

    }

    private fun saveData(context: Context, destination: Destination) {

        val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
        val requestCall = destinationService.addDestination(destination)

        requestCall.enqueue(object: Callback<Destination>{

            override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                if (response.isSuccessful) {
                    finish()
                    Toast.makeText(context, "Success", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<Destination>, t: Throwable) {

            }

        })

    }
}
