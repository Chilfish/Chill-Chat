package top.chilfish.chillchat.room.profile

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import top.chilfish.chillchat.data.Profile
import top.chilfish.chillchat.data.User_Table

@Dao
interface ProfileDao {
    @Query("SELECT * FROM $User_Table ORDER BY name ASC")
    fun getAll(): Flow<MutableList<Profile>>

    @Query("SELECT * FROM $User_Table WHERE id = :id")
    suspend fun getById(id: Long): Profile

    @Query("SELECT * FROM $User_Table WHERE name LIKE :name")
    fun getByName(name: String): Flow<MutableList<Profile>>

    @Insert
    suspend fun insert(profile: Profile)

    @Query("DELETE FROM $User_Table")
    suspend fun deleteAll()

    @Query("DELETE FROM $User_Table WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Update
    suspend fun update(profile: Profile)
}