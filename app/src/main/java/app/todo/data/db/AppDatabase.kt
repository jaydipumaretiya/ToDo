package app.todo.data.db

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import app.todo.data.converter.DateConverter
import app.todo.data.dao.ToDoDao
import app.todo.data.entity.ToDoEntity
import kotlinx.coroutines.CoroutineScope

@Database(
    version = 1,
    entities = [ToDoEntity::class],
    exportSchema = false
)
@TypeConverters(DateConverter::class)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun todoDao(): ToDoDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        @VisibleForTesting
        private val DATABASE_NAME = "ToDo"

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
//                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}
