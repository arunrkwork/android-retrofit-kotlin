package com.arunrk.booking_retrofit.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.arunrk.booking_retrofit.R
import com.arunrk.booking_retrofit.helpers.DestinationAdapter
import com.arunrk.booking_retrofit.models.Destination
import com.arunrk.booking_retrofit.services.DestinationService
import com.arunrk.booking_retrofit.services.ServiceBuilder
import kotlinx.android.synthetic.main.activity_destination_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DestinationListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_destination_list)
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mRecyclerView.hasFixedSize()

        btnAdd.setOnClickListener{
            startActivity(Intent(this, DestinationCreateActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadDestinations()
    }

    private fun loadDestinations() {
       // mRecyclerView.adapter = DestinationAdapter(SampleData.DESTINATIONS)

        val destinationService = ServiceBuilder.buildService(DestinationService::class.java)

        val filter = HashMap<String, String>()
//        filter.put("country", "India")
//        filter.put("count", "1")
       // filter["country"] = "India"
      //  filter["count"] = "1"

        //val requestCall = destinationService.getDestinationList("India", "1")
        val requestCall = destinationService.getDestinationList(filter)

//        requestCall.cancel()
//        requestCall.isCanceled

        requestCall.enqueue(object: Callback<List<Destination>> {

            override fun onResponse(call: Call<List<Destination>>
                                    , response: Response<List<Destination>>) {
                if (response.isSuccessful) {
                    val destinationList = response.body()!!
                    Log.d("Retrofit : ", "" + destinationList)
                    mRecyclerView.adapter = DestinationAdapter(destinationList)
                }
            }


            override fun onFailure(call: Call<List<Destination>>, t: Throwable) {

            }

        })
    }
}
