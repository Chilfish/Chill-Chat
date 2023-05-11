package top.chilfish.chillchat.room.contacts

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import top.chilfish.chillchat.data.Profile
import top.chilfish.chillchat.data.User_Table

@Database(entities = [Profile::class], version = 1, exportSchema = false)
abstract class ContactsDatabase : RoomDatabase() {
    abstract fun profileDao(): ContactsDao

    companion object {
        private const val DATABASE_NAME = "$User_Table.db"

        @Volatile
        private var INSTANCE: ContactsDatabase? = null

        fun getDatabase(
            context: Context
        ): ContactsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    ContactsDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}