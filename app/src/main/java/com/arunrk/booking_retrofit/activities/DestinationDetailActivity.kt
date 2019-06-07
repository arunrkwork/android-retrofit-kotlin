package com.arunrk.booking_retrofit.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.arunrk.booking_retrofit.R
import com.arunrk.booking_retrofit.models.Destination
import com.arunrk.booking_retrofit.services.DestinationService
import com.arunrk.booking_retrofit.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_destination_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationDetailActivity : AppCompatActivity() {

    companion object {

        const val ARG_ITEM_ID = "item_id"
    }

    var id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destination_detail)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bundle: Bundle? = intent.extras

        if (bundle?.containsKey(ARG_ITEM_ID)!!) {
            id = intent.getIntExtra(ARG_ITEM_ID, 0)
            loadDetails(id)
        }

        btnUpdate.setOnClickListener{
            val city = edCity.text.toString()
            val description = edDescription.text.toString()
            val country = edCountry.text.toString()

            val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
            val requestCall = destinationService.updateDestination(id, city, description, country)

            requestCall.enqueue(object : Callback<Destination>{

                override fun onResponse(call: Call<Destination>, response: Response<Destination>) {
                    if (response.isSuccessful) {
                        finish()
                        var updatedDestination = response.body()
                        Toast.makeText(this@DestinationDetailActivity, "Update Success", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(this@DestinationDetailActivity, "Failed", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Destination>, t: Throwable) {
                    Toast.makeText(this@DestinationDetailActivity, "Failed", Toast.LENGTH_LONG)
                        .show()
                }

            })
        }

        btnDelete.setOnClickListener {
            val destinationService = ServiceBuilder.buildService(DestinationService::class.java)
            val requestCall = destinationService.deleteDestination(id)

            requestCall.enqueue(object : Callback<Unit> {

                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    if (response.isSuccessful) {
                        finish()
                        Toast.makeText(this@DestinationDetailActivity, "Delete Success", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        Toast.makeText(this@DestinationDetailActivity, "Delete Failed", Toast.LENGTH_LONG)
                            .show()
                    }
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    Toast.makeText(this@DestinationDetailActivity, "Delete Failed", Toast.LENGTH_LONG)
                        .show()
                }

            })
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home) {
            navigateUpTo(Intent(this, DestinationListActivity::class.java))
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadDetails(id: Int) {
        val destinationService = ServiceBuilder
            .buildService(DestinationService::class.java)

        val requestCall = destinationService.getDestination(id)

        requestCall.enqueue(object: retrofit2.Callback<Destination>{

            override fun onResponse(call: Call<Destination>, response: Response<Destination>) {

                if (response.isSuccessful) {
                    val destination = response.body()
                    destination?.let {
                        edCity.setText(destination.city)
                        edCountry.setText(destination.country)
                        edDescription.setText(destination.description)

                        setTitle(destination.city)
                    }
                } else
                    Toast.makeText(this@DestinationDetailActivity
                        , "Failed"
                        , Toast.LENGTH_LONG
                    ).show()

            }

            override fun onFailure(call: Call<Destination>, t: Throwable) {
                Toast.makeText(this@DestinationDetailActivity
                    , "Failed to retrieve"
                    , Toast.LENGTH_LONG
                ).show()
            }
        })

    }
}
