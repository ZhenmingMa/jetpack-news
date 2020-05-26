package cn.sinoits.traffic.project.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cn.sinoits.traffic.project.data.entity.Joke
import cn.sinoits.traffic.project.data.entity.NewsType
import cn.sinoits.traffic.project.data.entity.Photo

@Database(
    entities = [Joke::class, Photo::class, NewsType::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getJokeDao(): JokeDao
    abstract fun getPhotoDao(): PhotoDao
    abstract fun getNewsTypeDao(): NewsTypeDao

    companion object {
        @Volatile
        var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context?): AppDatabase {

            return INSTANCE ?: synchronized(this) { INSTANCE ?: buildDatabase(context) }
        }

        private fun buildDatabase(context: Context?) =
            context?.let {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "project.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            } ?: let {
                throw IllegalStateException("AppDatabase  not init")
            }


    }
}