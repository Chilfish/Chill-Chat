package top.chilfish.chillchat.provider

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


private const val ACCOUNT_SP = "Account"
private val KEY_IS_LOGIN = booleanPreferencesKey("isLogin")
private val KEY_UID = stringPreferencesKey("uid")
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = ACCOUNT_SP)

// global user's state
var curUid: Long = -1
var isLoggedIn = MutableStateFlow(false)

/**
 * Provide user's account state
 */
object AccountProvider {
    private lateinit var dataStore: DataStore<Preferences>

    fun init(context: Context) {
        dataStore = context.dataStore

        CoroutineScope(Dispatchers.IO).launch {
            val pref = dataStore.data.first()

            isLoggedIn.value = pref[KEY_IS_LOGIN] ?: false
            curUid = pref[KEY_UID]?.toLongOrNull() ?: -1
        }
    }

    suspend fun setLogin(uid: Long) {
        curUid = uid
        isLoggedIn.value = true
        dataStore.edit {
            it[KEY_IS_LOGIN] = true
            it[KEY_UID] = uid.toString()
        }
    }

    suspend fun setLogout() {
        curUid = -1
        isLoggedIn.value = false
        dataStore.edit {
            it[KEY_IS_LOGIN] = false
            it[KEY_UID] = ""
        }
    }
}