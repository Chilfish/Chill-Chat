package top.chilfish.chillchat.room.messages

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import top.chilfish.chillchat.data.Message
import top.chilfish.chillchat.data.Message_Table

@Database(entities = [Message::class], version = 1, exportSchema = false)
abstract class MessageDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao

    companion object {
        private const val DATABASE_NAME = "$Message_Table.db"

        @Volatile
        private var INSTANCE: MessageDatabase? = null

        fun getDatabase(
            context: Context
        ): MessageDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    MessageDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}