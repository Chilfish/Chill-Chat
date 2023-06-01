package top.chilfish.chillchat.data.contacts

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import top.chilfish.chillchat.provider.curCid

@Dao
interface ContactsDao {
    @Transaction
    @Query("SELECT * FROM $User_Table ORDER BY cid ASC")
    fun getAll(): Flow<MutableList<Profile>>

    @Transaction
    @Query("SELECT * FROM $User_Table WHERE id = :id")
    suspend fun getById(id: String): Profile?

    @Transaction
    @Query("SELECT * FROM $User_Table WHERE cid LIKE :name")
    suspend fun getByName(name: String): MutableList<Profile>

    @Transaction
    @Query("SELECT * FROM $User_Table WHERE cid = :cid")
    fun getUser(cid: String = curCid): Flow<Profile>

    @Transaction
    @Insert
    suspend fun insert(profile: Profile)

    @Transaction
    @Query("DELETE FROM $User_Table WHERE cid != :cid")
    suspend fun deleteAll(cid: String = curCid)

    @Transaction
    @Query("DELETE FROM $User_Table WHERE id = :id")
    suspend fun deleteById(id: String): Int

    @Transaction
    @Update
    suspend fun update(profile: Profile): Int
}