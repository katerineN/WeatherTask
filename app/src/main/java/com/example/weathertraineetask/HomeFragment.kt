package com.example.weathertraineetask

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.AsyncNetwork
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment(R.layout.fragment_home) {

    var city:String = "moscow"
    var country: String = "ru"
    private val url = "https://api.openweathermap.org/data/2.5/weather"
    private val appid = "801b75fb48ba81d5d376deeb849b336d"

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val addr = view.findViewById(R.id.address) as TextView
        val upd = view.findViewById(R.id.updated_at) as TextView
        val st = view.findViewById(R.id.status) as TextView
        val t = view.findViewById(R.id.temp) as TextView
        val min = view.findViewById(R.id.temp_min) as TextView
        val max = view.findViewById(R.id.temp_max) as TextView

        val tempUrl: String = url + "?q=" + city + "," + country + "&units=metric&appid=" + appid
        Log.d("address", tempUrl)
        Log.d("MY_TAG","NEURA")
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(Request.Method.GET, tempUrl,
            { response ->
                try {
                    Log.d("MY_TAG","URA")
                    val jsonResponse = JSONObject(response)
                    val jsonArray = jsonResponse.getJSONArray("weather")
                    val jsonObjectWeather : JSONObject = jsonArray.getJSONObject(0)
                    val description = jsonObjectWeather.getString("description")
                    val jsonObjectMain = jsonResponse.getJSONObject("main")
                    val temp = jsonObjectMain.getDouble("temp")
                    val tempMin = "Min Temp: " + jsonObjectMain.getString("temp_min")+"°C"
                    val tempMax = "Max Temp: " + jsonObjectMain.getString("temp_max")+"°C"
                    val pressure = jsonObjectMain.getInt("pressure")
                    val humidity = jsonObjectMain.getInt("humidity")
                    val jsonObjectWind = jsonResponse.getJSONObject("wind")
                    val wind = jsonObjectWind.getString("speed")
                    val jsonObjectClouds = jsonResponse.getJSONObject("clouds")
                    val clouds = jsonObjectClouds.getString("all")
                    val jsonObjectSys = jsonResponse.getJSONObject("sys")

                    addr.text = city + "," + country
                    val updatedAt:Long = jsonResponse.getLong("dt")
                    upd.text =   SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt*1000))
                    println("HELLO"+SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt*1000)))
                    st.text = description
                    t.text = temp.toString() + "°C"
                    min.text = tempMin
                    max.text = tempMax
                } catch (e : Exception) {
                    Log.e("WeatherTask", "Problem with JSON")
                }
    }){
            Log.e("WeatherTask", it.toString())
        }
        queue.add(stringRequest)
        Log.d("MY_TAG","URAURA")
        return view
    }

}