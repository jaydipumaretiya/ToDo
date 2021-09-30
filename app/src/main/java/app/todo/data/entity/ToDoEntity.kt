package app.todo.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity(tableName = "todo_master")
class ToDoEntity : Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "title")
    var title: String = ""

    @ColumnInfo(name = "description")
    var description: String = ""

    @ColumnInfo(name = "types")
    var types: Int = 0

    @ColumnInfo(name = "date_time")
    var dateTime: Date? = Date()
}
