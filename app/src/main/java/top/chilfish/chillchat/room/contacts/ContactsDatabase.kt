package top.chilfish.chillchat.room.contacts

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import top.chilfish.chillchat.data.Profile
import top.chilfish.chillchat.data.User_Table

@Database(entities = [Profile::class], version = 1, exportSchema = false)
abstract class ContactsDatabase : RoomDatabase() {
    abstract fun contactsDao(): ContactsDao

    companion object {
        private const val DATABASE_NAME = "$User_Table.db"

        @Volatile
        private var INSTANCE: ContactsDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope,
        ): ContactsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactsDatabase::class.java,
                    DATABASE_NAME
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(ContactsDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class ContactsDatabaseCallback(
            private val scope: CoroutineScope
        ) : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO)  {
                        populateDatabase(database.contactsDao())
                    }
                }
            }
        }

        private suspend fun populateDatabase(profileDao: ContactsDao) {
            profileDao.deleteAll()
            initData.forEach { profileDao.insert(it) }
        }

        private const val Host = "chilfish.top"
        private const val AvatarBase = "https://p.$Host"

        private val initData = listOf(
            Profile(
                id = 1,
                name = "Chilfish",
                avatar = "$AvatarBase/avatar.webp",
                email = "Chilfish@$Host",
                bio = "Chilfish is a fish.",
            ),
            Profile(
                id = 2,
                name = "Organic Fish",
                avatar = "$AvatarBase/avatar1.webp",
                email = "OrganicFish@$Host",
                bio = "Organic Fish is a fish too.",
            )
        )
    }
}