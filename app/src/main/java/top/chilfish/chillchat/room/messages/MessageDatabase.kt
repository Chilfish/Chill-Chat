package top.chilfish.chillchat.room.messages

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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
            context: Context,
            scope: CoroutineScope
        ): MessageDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MessageDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(MessageDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class MessageDatabaseCallback(
            private val scope: CoroutineScope
        ) : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.messageDao())
                    }
                }
            }
        }

        private suspend fun populateDatabase(messageDao: MessageDao) {
            messageDao.deleteAll()
            initData.forEach { messageDao.insert(it) }
        }

        private val initData = listOf(
            Message(
                senderId = 1,
                receiverId = 0,
                message = "Hello",
                time = 1681142224000,
            )
        )
    }
}