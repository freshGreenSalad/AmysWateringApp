package com.example.amyswateringapp

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import java.time.LocalDateTime

@Entity
data class Plant(
    @PrimaryKey(autoGenerate = true) val plantId: Int = 0,
    val plantName: String = "",
    val WaterIntervalTime: Int = 0,
    val image: Uri = Uri.EMPTY,
    val NextWateringTime: LocalDateTime = LocalDateTime.now()
)


class UriConverters {
    @TypeConverter
    fun fromString(value: String?): Uri? {
        return if (value == null) null else Uri.parse(value)
    }

    @TypeConverter
    fun toString(uri: Uri?): String? {
        return uri?.toString()
    }
}


class LocalDateTimeConverter {
    @TypeConverter
    fun toDate( dateString: String?): LocalDateTime? {
        return if (dateString == null) null else  LocalDateTime.parse(dateString)
    }

    @TypeConverter
    fun toDateString( date: LocalDateTime?): String? {
        return if (date == null) null else date.toString()
    }
}