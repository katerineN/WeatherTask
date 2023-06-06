package com.example.weathertraineetask


import Database.WeatherDao
import HomeViewModel
import Models.WeatherM
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import com.example.weathertraineetask.databinding.FragmentHomeBinding


class HomeFragment() : Fragment() {

    var city: String = "moscow"
    var country: String = "ru"


    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    private fun observeWeatherData() {
        viewModel.weatherData.observe(viewLifecycleOwner) { weather ->
            updateUI(weather)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        observeWeatherData()
        viewModel.fetchWeatherData(city, country)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun updateUI(weather: WeatherM) {
        binding.address.text = "${city}, ${country}"
        binding.updatedAt.text = weather.updatedAt
        binding.status.text = weather.description
        binding.temp.text = "${weather.temperature}°C"
        binding.tempMin.text = "Min Temp: " + weather.minTemperature
        binding.tempMax.text = "Max Temp: " + weather.maxTemperature
        binding.pressure.text = weather.pressure.toString()
        binding.humidity.text = "${weather.humidity}%"
        binding.wind.text = "${weather.windSpeed} m/s"
        binding.sunrise.text = SimpleDateFormat("h:mm a", Locale.ENGLISH).format(Date((weather.sunrise).toLong() * 1000))
        binding.sunset.text = SimpleDateFormat("h:mm a", Locale.ENGLISH).format(Date((weather.sunset).toLong() * 1000))
    }


}