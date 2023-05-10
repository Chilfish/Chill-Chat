package top.chilfish.chillchat.room.profile

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import top.chilfish.chillchat.data.Profile
import top.chilfish.chillchat.data.User_Table

@Database(entities = [Profile::class], version = 1, exportSchema = false)
abstract class ProfileDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao

    companion object {
        private const val DATABASE_NAME = "$User_Table.db"

        @Volatile
        private var INSTANCE: ProfileDatabase? = null

        fun getDatabase(
            context: Context
        ): ProfileDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    ProfileDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}