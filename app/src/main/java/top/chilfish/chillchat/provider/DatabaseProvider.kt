package top.chilfish.chillchat.provider

import android.content.Context
import top.chilfish.chillchat.room.chatslist.ChatsListDatabase
import top.chilfish.chillchat.room.chatslist.ChatsListRepository
import top.chilfish.chillchat.room.messages.MessageDatabase
import top.chilfish.chillchat.room.messages.MessageRepository
import top.chilfish.chillchat.room.contacts.ContactsDatabase
import top.chilfish.chillchat.room.contacts.ContactsRepository

object DatabaseProvider {

    lateinit var contactsRepository: ContactsRepository
        private set

    lateinit var messageRepository: MessageRepository
        private set

    lateinit var chatsListRepository: ChatsListRepository
        private set

    fun init(context: Context) {
        val contactsDatabase by lazy {
            ContactsDatabase.getDatabase(context)
        }

        val messageDatabase by lazy {
            MessageDatabase.getDatabase(context)
        }

        val chatsListDatabase by lazy {
            ChatsListDatabase.getDatabase(context)
        }

        contactsRepository = ContactsRepository(contactsDatabase.profileDao())
        messageRepository = MessageRepository(messageDatabase.messageDao())
        chatsListRepository = ChatsListRepository(chatsListDatabase.chatsListDao())
    }
}