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
private val KEY_CID = stringPreferencesKey("cid")
private val KEY_ID = stringPreferencesKey("id")
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = ACCOUNT_SP)

// global user's state
var curCid: String = ""
var curId: String = ""
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
            curCid = pref[KEY_CID] ?: ""
            curId = pref[KEY_ID] ?: ""
        }
    }

    suspend fun setLogin(id: String, cid: String) {
        curCid = cid
        curId = id
        isLoggedIn.value = true
        dataStore.edit {
            it[KEY_IS_LOGIN] = true
            it[KEY_CID] = cid
            it[KEY_ID] = id
        }
    }

    suspend fun setLogout() {
        curCid = ""
        curId = ""
        isLoggedIn.value = false
        dataStore.edit {
            it[KEY_IS_LOGIN] = false
            it[KEY_CID] = ""
            it[KEY_ID] = ""
        }
    }
}
