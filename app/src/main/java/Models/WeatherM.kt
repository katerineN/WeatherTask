package Models

data class WeatherM(
    val description: String,
    val temperature: Double,
    val minTemperature: String,
    val maxTemperature: String,
    val updatedAt: String,
    val pressure: Int,
    val humidity: Int,
    val windSpeed: String,
    val sunrise: Int,
    val sunset: Int
)
