package Database

import Models.WeatherM
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WeatherRepository(private val context: Context) {

    private var cachedWeather: WeatherM? = null

    fun getWeatherData(city: String, country: String, callback: (WeatherM) -> Unit) {
        val url = "https://api.openweathermap.org/data/2.5/weather"
        val appid = "801b75fb48ba81d5d376deeb849b336d"

        val tempUrl: String = "$url?q=$city,$country&units=metric&appid=$appid"
        val queue = Volley.newRequestQueue(context)
        val stringRequest = StringRequest(
            Request.Method.GET, tempUrl,
            { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val jsonArray = jsonResponse.getJSONArray("weather")
                    val jsonObjectWeather: JSONObject = jsonArray.getJSONObject(0)
                    val description = jsonObjectWeather.getString("description")
                    val jsonObjectMain = jsonResponse.getJSONObject("main")
                    val temp = jsonObjectMain.getDouble("temp")
                    val tempMin = jsonObjectMain.getString("temp_min")
                    val tempMax = jsonObjectMain.getString("temp_max")
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

                    val weather = WeatherM(
                        description,
                        temp,
                        tempMin,
                        tempMax,
                        upd,
                        pressure,
                        humidity,
                        wind,
                        sunrise,
                        sunset
                    )

                    // Кэширование данных
                    cachedWeather = weather

                    callback.invoke(weather)
                } catch (e: Exception) {
                    Log.e("WeatherTask", "Problem with JSON")
                }
            }) {
            Log.e("WeatherTask", it.toString())
        }
        queue.add(stringRequest)
    }

    fun getCachedWeatherData(): LiveData<WeatherM?> {
        val liveData = MutableLiveData<WeatherM?>()
        liveData.value = cachedWeather
        return liveData
    }
}
