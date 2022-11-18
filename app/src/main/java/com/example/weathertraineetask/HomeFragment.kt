package com.example.weathertraineetask

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import org.json.JSONObject
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

        val tempUrl: String = url + "?q=" + city + "," + country + "appid=" + appid
        val stringRequest : StringRequest = StringRequest(Request.Method.POST, tempUrl,
            { response ->
                println("HELLO")
                try {
                    println("HELLO2")
                    val jsonResponse = JSONObject(response);
                    val jsonArray = jsonResponse.getJSONArray("weather")
                    val jsonObjectWeather : JSONObject = jsonArray.getJSONObject(0)
                    val description = jsonObjectWeather.getString("description")
                    val jsonObjectMain = jsonResponse.getJSONObject("main")
                    val temp = jsonObjectMain.getDouble("temp") - 273.15
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
                    upd.text =   "Updated at: "+ SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date(updatedAt*1000))
                    st.text = description
                    t.text = temp.toString() + "°C"
                    min.text = tempMin
                    max.text = tempMax
                } catch (e : Exception) {
                    Log.e("WeatherTask", "Problem with JSON")
                }
            }) {}
        return view
    }

}