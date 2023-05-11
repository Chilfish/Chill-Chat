package top.chilfish.chillchat.data

import okhttp3.internal.immutableListOf
import okhttp3.internal.toImmutableList
import top.chilfish.chillchat.data.chatslist.Chats
import top.chilfish.chillchat.data.contacts.AvatarHost
import top.chilfish.chillchat.data.contacts.Host
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.data.messages.Message


private const val curId = 3L

val Contacts = immutableListOf(
    Profile(
        id = curId,
        name = "Chilfish",
        avatar = "$AvatarHost/avatar.webp",
        email = "Chilfish@$Host",
        bio = "Chilfish is a fish that lives in the sea."
    ),
    Profile(
        id = 1,
        name = "Organic Fish",
        avatar = "$AvatarHost/avatar1.webp",
        email = "Organic_Fish@$Host",
        bio = "Organic Fish is a fish that is organic."
    ),
    Profile(
        id = 2,
        name = "Blue Fish",
        avatar = "$AvatarHost/avatar2.webp",
        email = "Blue_Fish@$Host",
        bio = "Blue is a fish that has blue eyes."
    ),
)

val Messages = immutableListOf(
    Message(
        id = 7,
        senderId = curId,
        receiverId = 1,
        message = "Hello, Organic Fish!",
        time = 1681142224000
    ),
    Message(
        id = 1,
        senderId = 1,
        receiverId = curId,
        message = "Hello, Chilfish!",
        time = 1681142228000
    ),
    Message(
        id = 2,
        senderId = curId,
        receiverId = 2,
        message = "How are you?",
        time = 1681142294000
    ),
    Message(
        id = 3,
        senderId = 1,
        receiverId = curId,
        message = "I'm fine, thank you.",
        time = 1681142264000
    ),
    Message(
        id = 4,
        senderId = 2,
        receiverId = curId,
        message = "And you?",
        time = 1681142824000
    ),

    Message(
        id = 5,
        senderId = 1,
        receiverId = curId,
        message = "I'm fine too.",
        time = 1681142624000
    ),
    Message(
        id = 6,
        senderId = 2,
        receiverId = curId,
        message = "Goodbye.",
        time = 1681142274000
    ),
)
    .sortedBy { it.time }
    .reversed()


fun filterChat(chatterId: Long, message: Message) =
    (message.receiverId == chatterId && message.senderId == curId) ||
            (message.receiverId == curId && message.senderId == chatterId)

val ChatsList = Contacts.mapIndexed { i, contact ->
    val message = Messages.find { filterChat(contact.id, it) }
        ?: return@mapIndexed null
    Chats(
        id = i.toLong(),
        chatterId = contact.id,
        lastMessage = message.message,
        lastTime = message.time
    )
}
    .filterNotNull()
    .toImmutableList()