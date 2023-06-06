package com.example.weathertraineetask

import Models.WeatherData
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.weathertraineetask.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //setContentView(R.layout.activity_main)
        val db = Room.databaseBuilder(
                this,
                WeatherData::class.java, "weather-database"
            ).build()
        val mainFragment=HomeFragment()
        val weekFragment=WeekWeatherFragment()
        val locationFragment=LocationFragment()

        setCurrentFragment(mainFragment)

        binding.bottomNav.setOnItemSelectedListener{ it ->
            when (it.itemId) {
                R.id.item1 -> setCurrentFragment(mainFragment)
                R.id.item2 -> setCurrentFragment(weekFragment)
                R.id.item3 -> setCurrentFragment(locationFragment)

            }
            true
        }
    }
    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.mainFragment,fragment)
            commit()
        }
}