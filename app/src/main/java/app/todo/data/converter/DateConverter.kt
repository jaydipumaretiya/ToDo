package app.todo.data.converter

import androidx.room.TypeConverter
import java.math.BigDecimal
import java.util.*

class DateConverter {

    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return if (timestamp == null) null else Date(timestamp)
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun stringToBigDecimal(value: String): BigDecimal {
        val v = BigDecimal(value)
        v.setScale(2)
        return v
    }

    @TypeConverter
    fun bigDecimalToString(value: BigDecimal): String {
        return value.setScale(2).toString()
    }
}
