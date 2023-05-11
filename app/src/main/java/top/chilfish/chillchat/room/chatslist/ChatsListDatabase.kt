package top.chilfish.chillchat.room.chatslist

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import top.chilfish.chillchat.data.Chat_Table
import top.chilfish.chillchat.data.Chats

@Database(entities = [Chats::class], version = 1, exportSchema = false)
abstract class ChatsListDatabase : RoomDatabase() {
    abstract fun chatsListDao(): ChatsListDao

    companion object {
        private const val DATABASE_NAME = "$Chat_Table.db"

        @Volatile
        private var INSTANCE: ChatsListDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ChatsListDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ChatsListDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(ChatsListDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class ChatsListDatabaseCallback(
            private val scope: CoroutineScope
        ) : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.chatsListDao())
                    }
                }
            }
        }

        private suspend fun populateDatabase(chatsListDao: ChatsListDao) {
            chatsListDao.deleteAll()
            initData.forEach { chatsListDao.insert(it) }
        }

        private val initData = listOf(
            Chats(
                chatterId = 2,
                lastMessage = "Hello",
                lastTime = 1681142224000,
            )
        )
    }
}