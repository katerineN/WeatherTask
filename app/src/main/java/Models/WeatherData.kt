package Models

import Database.WeatherDao
import androidx.room.Database
import androidx.room.RoomDatabase


//data class WeatherData (
//    val id: Long,
//    val updated_at: String,
//    val temperature: String,
//    val description: String,
//    ) {
//
//    fun toWeatherDbEntity(): WeatherDbEntity = WeatherDbEntity(
//        id = id,
//        updated_at = updated_at,
//        temperature = temperature,
//        description = description
//    )
//}

@Database(entities = [WeatherDbEntity::class], version = 1)
abstract class WeatherData : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}
