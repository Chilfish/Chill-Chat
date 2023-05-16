package top.chilfish.chillchat.provider

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import top.chilfish.chillchat.data.ChillChatDatabase
import top.chilfish.chillchat.data.repository.ChatsListRepository
import top.chilfish.chillchat.data.repository.ContactsRepository
import top.chilfish.chillchat.data.repository.MessageRepository

object RepoProvider {

    lateinit var chatsRepo: ChatsListRepository
        private set

    lateinit var messRepo: MessageRepository
        private set

    lateinit var contactsRepo: ContactsRepository
        private set

    fun init(db: ChillChatDatabase, scope: CoroutineScope) {
        chatsRepo = ChatsListRepository(db.chatsDao())
        messRepo = MessageRepository(db.MessageDao())
        contactsRepo = ContactsRepository(db.ContactsDao())

        // to init database file
        scope.launch { chatsRepo.getAll() }
    }
}