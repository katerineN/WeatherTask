package com.example.weathertraineetask

import Database.DBHelper
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import com.example.weathertraineetask.databinding.FragmentHomeBinding


class HomeFragment : Fragment(R.layout.fragment_home) {

    var city: String = "moscow"
    var country: String = "ru"
    private val url = "https://api.openweathermap.org/data/2.5/weather"
    private val appid = "801b75fb48ba81d5d376deeb849b336d"

    private var _mainBinding: FragmentHomeBinding? = null
    private val mainBinding get() = _mainBinding

    private fun getWeatherData() {
        val db = context?.let { DBHelper(it, null) }
        val tempUrl: String = url + "?q=" + city + "," + country + "&units=metric&appid=" + appid
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(Request.Method.GET, tempUrl,
            { response ->
                try {
                    Log.d("MY_TAG","URA")
                    val jsonResponse = JSONObject(response)
                    val jsonArray = jsonResponse.getJSONArray("weather")
                    val jsonObjectWeather: JSONObject = jsonArray.getJSONObject(0)
                    val description = jsonObjectWeather.getString("description")
                    val jsonObjectMain = jsonResponse.getJSONObject("main")
                    val temp = jsonObjectMain.getDouble("temp")
                    val tempMin = "Min Temp: " + jsonObjectMain.getString("temp_min") + "°C"
                    val tempMax = "Max Temp: " + jsonObjectMain.getString("temp_max") + "°C"
                    val pressure = jsonObjectMain.getInt("pressure")
                    val humidity = jsonObjectMain.getInt("humidity")
                    val jsonObjectWind = jsonResponse.getJSONObject("wind")
                    val wind = jsonObjectWind.getString("speed")
                    val sys = jsonResponse.getJSONObject("sys")
                    val sunrise = sys.getInt("sunrise")
                    val sunset = sys.getInt("sunset")
                    val updatedAt: Long = jsonResponse.getLong("dt")
                    val upd = SimpleDateFormat(
                        "dd/MM/yyyy hh:mm a",
                        Locale.ENGLISH
                    ).format(Date(updatedAt * 1000))

                    //здесь сохранение в бд
                    db?.addWeather(upd,"$temp°C",description)
                    //Toast.makeText(context, description + " added to database", Toast.LENGTH_LONG).show()
                    setData(description, temp, tempMin, tempMax, upd, pressure, humidity, wind, sunrise, sunset)
                } catch (e: Exception) {
                    Log.e("WeatherTask", "Problem with JSON")
                }
            }) {
            Log.e("WeatherTask", it.toString())
        }
        queue.add(stringRequest)
    }

    @SuppressLint("SetTextI18n")
    private fun setData(
        description: String,
        tempp: Double,
        tempMin: String,
        tempMax: String,
        upd: String,
        pressure : Int,
        humidity: Int,
        wind: String,
        sunrise: Int,
        sunset: Int
    ) {
        Log.d("MY_TAG","URA2")
        mainBinding?.address?.text = city.uppercase() + ", " + country.uppercase()
        mainBinding?.updatedAt?.text = (upd)
        mainBinding?.status?.text = description.uppercase()
        mainBinding?.temp?.text = "$tempp°C"
        mainBinding?.tempMin?.text = (tempMin)
        mainBinding?.tempMax?.text = (tempMax)
        mainBinding?.pressure?.text = "$pressure"
        mainBinding?.humidity?.text = "$humidity%"
        mainBinding?.wind?.text = "$wind m/s"
        mainBinding?.sunrise?.text = SimpleDateFormat("h:mm a", Locale.ENGLISH).format(Date((sunrise).toLong() * 1000))
        mainBinding?.sunset?.text = SimpleDateFormat("h:mm a", Locale.ENGLISH).format(Date((sunset).toLong() * 1000))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val view = inflater.inflate(R.layout.fragment_home, container, false)

        _mainBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        getWeatherData();
        val view = mainBinding?.root

        return view
    }


    override fun onDestroy() {
        super.onDestroy()
        _mainBinding = null
    }

}