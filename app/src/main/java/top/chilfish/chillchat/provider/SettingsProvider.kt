package top.chilfish.chillchat.provider

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

private const val defaultHost = "http://192.168.144.39:8000"
var BaseHost = MutableStateFlow(defaultHost)
    private set

private const val SETTINGS_SP = "Settings"
private val KEY_HOST = stringPreferencesKey("host")
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SETTINGS_SP)

object SettingsProvider {

    private lateinit var dataStore: DataStore<Preferences>

    fun init(context: Context) {
        dataStore = context.dataStore
        CoroutineScope(Dispatchers.IO).launch {
            val pref = dataStore.data.first()
            BaseHost.value = pref[KEY_HOST] ?: defaultHost
        }
    }

    suspend fun setHost(host: String) {
        BaseHost.value = host
        dataStore.edit {
            it[KEY_HOST] = host
        }
    }
}
