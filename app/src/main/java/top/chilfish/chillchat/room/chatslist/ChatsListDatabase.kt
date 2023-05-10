package top.chilfish.chillchat.room.chatslist

import androidx.room.Database
import androidx.room.RoomDatabase
import top.chilfish.chillchat.data.Chat_Table
import top.chilfish.chillchat.data.ChatsList

@Database(entities = [ChatsList::class], version = 1, exportSchema = false)
abstract class ChatsListDatabase : RoomDatabase() {
    abstract fun chatsListDao(): ChatsListDao

    companion object {
        private const val DATABASE_NAME = "$Chat_Table.db"

        @Volatile
        private var INSTANCE: ChatsListDatabase? = null

        fun getDatabase(
            context: android.content.Context
        ): ChatsListDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = androidx.room.Room.databaseBuilder(
                    context.applicationContext,
                    ChatsListDatabase::class.java,
                    DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}