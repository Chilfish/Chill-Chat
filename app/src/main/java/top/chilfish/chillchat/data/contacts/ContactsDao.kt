package top.chilfish.chillchat.data.contacts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import top.chilfish.chillchat.provider.curUid

@Dao
interface ContactsDao {
    @Query("SELECT * FROM $User_Table ORDER BY name ASC")
    fun getAll(): Flow<MutableList<Profile>>

    @Query("SELECT * FROM $User_Table WHERE id = :id")
    suspend fun getById(id: Long): Profile?

    @Query("SELECT * FROM $User_Table WHERE name LIKE :name")
    suspend fun getByName(name: String): MutableList<Profile>

    @Query("SELECT * FROM $User_Table WHERE id = :id")
    fun getUser(id: Long = curUid): Flow<Profile>

    @Insert
    suspend fun insert(profile: Profile)

    @Query("DELETE FROM $User_Table")
    suspend fun deleteAll()

    @Query("DELETE FROM $User_Table WHERE id = :id")
    suspend fun deleteById(id: Long): Int

    @Update
    suspend fun update(profile: Profile): Int
}