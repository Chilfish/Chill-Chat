package top.chilfish.chillchat.provider

import top.chilfish.chillchat.data.ChillChatDatabase
import top.chilfish.chillchat.data.chatslist.ChatsListRepository
import top.chilfish.chillchat.data.contacts.ContactsRepository
import top.chilfish.chillchat.data.messages.MessageRepository

object RepoProvider {
    private lateinit var db: ChillChatDatabase

    lateinit var chatsRepo: ChatsListRepository
        private set

    lateinit var messRepo: MessageRepository
        private set

    lateinit var contactsRepo: ContactsRepository
        private set

    fun init(db: ChillChatDatabase) {
        this.db = db

        chatsRepo = ChatsListRepository(db.chatsDao())
        messRepo = MessageRepository(db.MessageDao())
        contactsRepo = ContactsRepository(db.ContactsDao())
    }
}