import Database.WeatherRepository
import Models.WeatherM
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WeatherRepository(application)
    private val _weatherData = MutableLiveData<WeatherM>()
    val weatherData: LiveData<WeatherM> = _weatherData

    // Метод для получения данных о погоде и обновления LiveData объекта
    fun fetchWeatherData(city: String, country: String) {
        repository.getWeatherData(city, country) { weather ->
            _weatherData.value = weather
        }
    }
}
