package Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Weather")
data class WeatherDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "updated_at") val updated_at: String,
    @ColumnInfo(name = "temperature") val temperature: String,
    @ColumnInfo(name = "description") val description: String
)