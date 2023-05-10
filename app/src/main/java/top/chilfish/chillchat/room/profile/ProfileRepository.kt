package top.chilfish.chillchat.room.profile

import top.chilfish.chillchat.data.Profile

class ProfileRepository(private val dao: ProfileDao) {
    val allUsers = dao.getAll()

    suspend fun insert(profile: Profile) = dao.insert(profile)

    suspend fun delete(id: Long) = dao.deleteById(id)

    suspend fun update(profile: Profile) = dao.update(profile)

    suspend fun getById(id: Long) = dao.getById(id)

    fun getByName(name: String) = dao.getByName(name)
}