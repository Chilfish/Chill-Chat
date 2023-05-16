package top.chilfish.chillchat.data.repository

import top.chilfish.chillchat.data.contacts.ContactsDao
import top.chilfish.chillchat.data.contacts.Profile
import top.chilfish.chillchat.provider.curUid
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactsRepository @Inject constructor(
    private val dao: ContactsDao
) {
    suspend fun allUsers() = dao.getAll()

    suspend fun insert(profile: Profile) = dao.insert(profile)

    suspend fun delete(id: Long) = dao.deleteById(id)

    suspend fun update(profile: Profile) = dao.update(profile)

    suspend fun getById(id: Long) = dao.getById(id)

    suspend fun getUser(id: Long = curUid) = dao.getById(id)

    suspend fun getByName(name: String) = dao.getByName(name)
}