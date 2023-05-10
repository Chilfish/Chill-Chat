package top.chilfish.chillchat.provider

import android.content.Context
import top.chilfish.chillchat.data.Profile
import top.chilfish.chillchat.room.chatslist.ChatsListDatabase
import top.chilfish.chillchat.room.chatslist.ChatsListRepository
import top.chilfish.chillchat.room.messages.MessageDatabase
import top.chilfish.chillchat.room.messages.MessageRepository
import top.chilfish.chillchat.room.profile.ProfileDatabase
import top.chilfish.chillchat.room.profile.ProfileRepository

object DatabaseProvider {

    lateinit var profileRepository: ProfileRepository
        private set

    lateinit var messageRepository: MessageRepository
        private set

    lateinit var chatsListRepository: ChatsListRepository
        private set

    fun init(context: Context) {
        val profileDatabase by lazy {
            ProfileDatabase.getDatabase(context)
        }

        val messageDatabase by lazy {
            MessageDatabase.getDatabase(context)
        }

        val chatsListDatabase by lazy {
            ChatsListDatabase.getDatabase(context)
        }

        profileRepository = ProfileRepository(profileDatabase.profileDao())
        messageRepository = MessageRepository(messageDatabase.messageDao())
        chatsListRepository = ChatsListRepository(chatsListDatabase.chatsListDao())
    }
}