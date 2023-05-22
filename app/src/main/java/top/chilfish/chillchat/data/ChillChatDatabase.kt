package top.chilfish.chillchat.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import top.chilfish.chillchat.data.chatslist.Chats
import top.chilfish.chillchat.data.chatslist.ChatsListDao
import top.chilfish.chillchat.data.contacts.ContactsDao
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.messages.Message
import top.chilfish.chillchat.data.messages.MessageDao

@Database(
    entities = [Chats::class, Message::class, Profile::class],
    version = 1,
    exportSchema = false
)
abstract class ChillChatDatabase : RoomDatabase() {
    abstract fun chatsDao(): ChatsListDao
    abstract fun MessageDao(): MessageDao
    abstract fun ContactsDao(): ContactsDao

    companion object {
        private const val DATABASE_NAME = "ChillChat.db"

        @Volatile
        private var INSTANCE: ChillChatDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): ChillChatDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ChillChatDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
//                    .addCallback(ChillChatDatabaseCallback(scope))
                    .build()
                    .also { INSTANCE = it }
            }
        }

        private class ChillChatDatabaseCallback(
            private val scope: CoroutineScope
        ) : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(
                            database.chatsDao(),
                            database.MessageDao(),
                            database.ContactsDao()
                        )
                    }
                }
            }
        }

        private suspend fun populateDatabase(
            chatsListDao: ChatsListDao,
            messageDao: MessageDao,
            contactsDao: ContactsDao
        ) {
            chatsListDao.deleteAll()
            messageDao.deleteAll()
            contactsDao.deleteAll()

            try {
                Contacts.forEach { contactsDao.insert(it) }
                Messages.forEach { messageDao.insert(it) }
                ChatsList.forEach { chatsListDao.insert(it) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}