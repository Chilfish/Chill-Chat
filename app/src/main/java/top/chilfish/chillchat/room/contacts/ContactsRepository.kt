package top.chilfish.chillchat.room.contacts

import top.chilfish.chillchat.data.Profile
import top.chilfish.chillchat.provider.curUid

class ContactsRepository(private val dao: ContactsDao) {
    val allUsers = dao.getAll()

    suspend fun insert(profile: Profile) = dao.insert(profile)

    suspend fun delete(id: Long) = dao.deleteById(id)

    suspend fun update(profile: Profile) = dao.update(profile)

    suspend fun getById(id: Long) = dao.getById(id)

    suspend fun getUser(id: Long = curUid) = dao.getById(id)

    fun getByName(name: String) = dao.getByName(name)
}