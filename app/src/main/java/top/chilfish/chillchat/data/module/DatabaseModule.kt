package top.chilfish.chillchat.data.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import top.chilfish.chillchat.data.ChillChatDatabase
import top.chilfish.chillchat.data.chatslist.ChatsListDao
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideChatsDao(db: ChillChatDatabase): ChatsListDao =
        db.chatsDao()

    @Provides
    @Singleton
    fun provideMessageDao(db: ChillChatDatabase) =
        db.MessageDao()

    @Provides
    @Singleton
    fun provideContactsDao(db: ChillChatDatabase) =
        db.ContactsDao()

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        @ApplicationScope scope: CoroutineScope,
    ): ChillChatDatabase =
        ChillChatDatabase.getDatabase(context, scope)
}