package top.chilfish.chillchat.data.contacts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ContactsDao {
    @Query("SELECT * FROM $User_Table ORDER BY name ASC")
    suspend fun getAll(): MutableList<Profile>

    @Query("SELECT * FROM $User_Table WHERE id = :id")
    suspend fun getById(id: Long): Profile?

    @Query("SELECT * FROM $User_Table WHERE name LIKE :name")
    suspend fun getByName(name: String): MutableList<Profile>

    @Insert
    suspend fun insert(profile: Profile)

    @Query("DELETE FROM $User_Table")
    suspend fun deleteAll()

    @Query("DELETE FROM $User_Table WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Update
    suspend fun update(profile: Profile)
}