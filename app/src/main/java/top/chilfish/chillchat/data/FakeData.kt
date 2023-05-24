package top.chilfish.chillchat.data
//
//import okhttp3.internal.immutableListOf
//import okhttp3.internal.toImmutableList
//import top.chilfish.chillchat.data.chatslist.Chats
//import top.chilfish.chillchat.data.contacts.Host
//import top.chilfish.chillchat.data.contacts.Profile
//import top.chilfish.chillchat.data.messages.Message
//
//
//private const val curId = "646ccece9c566de7a7a9b0e5"
//
//val Contacts = immutableListOf(
//    Profile(
//        cid = "chilfish",
//        nickname = "Chilfish",
//        email = "Chilfish@$Host",
//        bio = "Chilfish is a fish that lives in the sea.",
//        avatar = "avatar.webp"
//    ),
//    Profile(
//        cid = "organic_fish",
//        nickname = "Organic Fish",
//        email = "Organic_Fish@$Host",
//        bio = "Organic Fish is a fish that is organic.",
//        avatar = "avatar1.webp"
//    ),
//    Profile(
//        cid = "blue_fish",
//        nickname = "Blue Fish",
//        email = "Blue_Fish@$Host",
//        bio = "Blue is a fish that has blue eyes.",
//        avatar = "avatar2.webp"
//    ),
//)
//
//val Messages = immutableListOf(
//    Message(
//        senderId = curId,
//        receiverId = 1,
//        message = "Hello, Organic Fish!",
//        time = 1681142224000
//    ),
//    Message(
//        senderId = 1,
//        receiverId = curId,
//        message = "Hello, Chilfish!",
//        time = 1681142228000
//    ),
//    Message(
//        senderId = curId,
//        receiverId = 2,
//        message = "How are you?",
//        time = 1681142294000
//    ),
//    Message(
//        senderId = 1,
//        receiverId = curId,
//        message = "I'm fine, thank you.",
//        time = 1681142264000
//    ),
//    Message(
//        senderId = 2,
//        receiverId = curId,
//        message = "And you?",
//        time = 1681142824000
//    ),
//
//    Message(
//        senderId = 1,
//        receiverId = curId,
//        message = "I'm fine too.",
//        time = 1681142624000
//    ),
//    Message(
//        senderId = 2,
//        receiverId = curId,
//        message = "Goodbye.",
//        time = 1681142274000
//    ),
//    Message(
//        senderId = curId,
//        receiverId = 1,
//        message = "Goodbye.",
//        time = 1681142274000
//    ),
//    Message(
//        senderId = 1,
//        receiverId = curId,
//        message = "Hey there!",
//        time = 1632977224000
//    ),
//    Message(
//        senderId = 2,
//        receiverId = curId,
//        message = "What's up?",
//        time = 1632977290000
//    ),
//    Message(
//        senderId = curId,
//        receiverId = 1,
//        message = "Not much, just hanging out.",
//        time = 1632977332000
//    ),
//    Message(
//        senderId = 2,
//        receiverId = curId,
//        message = "Do you want to grab lunch later?",
//        time = 1632977411000
//    ),
//    Message(
//        senderId = 1,
//        receiverId = curId,
//        message = "Sure, what time?",
//        time = 1632977487000
//    ),
//    Message(
//        senderId = curId,
//        receiverId = 2,
//        message = "I'm free at noon.",
//        time = 1632977556000
//    ),
//    Message(
//        senderId = 1,
//        receiverId = curId,
//        message = "Noon works for me too.",
//        time = 1632977623000
//    ),
//    Message(
//        senderId = curId,
//        receiverId = 1,
//        message = "Great, let's meet at the park.",
//        time = 1632977700000
//    ),
//    Message(
//        senderId = 2,
//        receiverId = curId,
//        message = "Sounds good.",
//        time = 1632977774000
//    )
//)
//    .sortedBy { it.time }
//    .reversed()
//
//
//fun filterChat(chatterId: String, message: Message) =
//    (message.receiverId == chatterId && message.senderId == curId) ||
//            (message.receiverId == curId && message.senderId == chatterId)
//
//val ChatsList = Contacts.map { contact ->
//    val message = Messages.find { filterChat(contact.id, it) }
//        ?: return@map null
//    Chats(
//        chatterId = contact.id,
//        lastMessage = message.message,
//        lastTime = message.time
//    )
//}
//    .filterNotNull()
//    .toImmutableList()