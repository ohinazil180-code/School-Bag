package com.example.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "digital_schoolbag_prefs")

enum class UserRole {
    STUDENT_PARENT,
    ADMIN_TEACHER
}

class PreferencesManager(private val context: Context) {

    companion object {
        private val KEY_DARK_MODE = booleanPreferencesKey("pref_dark_mode")
        private val KEY_USER_ROLE = stringPreferencesKey("pref_user_role")
        private val KEY_ACTIVE_STUDENT_ID = stringPreferencesKey("pref_active_student_id")
        private val KEY_FIREBASE_RTDB_URL = stringPreferencesKey("pref_firebase_rtdb_url")
        private val KEY_ADMIN_EMAIL = stringPreferencesKey("pref_admin_email")
        private val KEY_IS_LOGGED_IN = booleanPreferencesKey("pref_is_logged_in")
        private val KEY_LOGGED_IN_USERNAME = stringPreferencesKey("pref_logged_in_username")
        private val KEY_LOGGED_IN_USER_NAME = stringPreferencesKey("pref_logged_in_user_name")
    }

    val isDarkMode: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_DARK_MODE] ?: false
    }

    val userRole: Flow<UserRole> = context.dataStore.data.map { prefs ->
        val roleStr = prefs[KEY_USER_ROLE] ?: UserRole.STUDENT_PARENT.name
        try {
            UserRole.valueOf(roleStr)
        } catch (e: Exception) {
            UserRole.STUDENT_PARENT
        }
    }

    val activeStudentId: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_ACTIVE_STUDENT_ID] ?: "std_01"
    }

    val firebaseRtdbUrl: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_FIREBASE_RTDB_URL] ?: "https://digital-school-bag-default-rtdb.firebaseio.com"
    }

    val adminEmail: Flow<String> = context.dataStore.data.map { prefs ->
        prefs[KEY_ADMIN_EMAIL] ?: "admin@digitalschoolbag.edu"
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[KEY_IS_LOGGED_IN] ?: false // No free demo access, requires explicit authentication
    }

    val loggedInUsername: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[KEY_LOGGED_IN_USERNAME]
    }

    val loggedInUserName: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[KEY_LOGGED_IN_USER_NAME]
    }

    suspend fun setDarkMode(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[KEY_DARK_MODE] = enabled
        }
    }

    suspend fun setUserRole(role: UserRole) {
        context.dataStore.edit { prefs ->
            prefs[KEY_USER_ROLE] = role.name
        }
    }

    suspend fun setActiveStudentId(studentId: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_ACTIVE_STUDENT_ID] = studentId
        }
    }

    suspend fun setFirebaseRtdbUrl(url: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_FIREBASE_RTDB_URL] = url
        }
    }

    suspend fun setLoggedIn(
        loggedIn: Boolean,
        role: UserRole = UserRole.STUDENT_PARENT,
        username: String = "",
        fullName: String = "",
        studentId: String? = null
    ) {
        context.dataStore.edit { prefs ->
            prefs[KEY_IS_LOGGED_IN] = loggedIn
            prefs[KEY_USER_ROLE] = role.name
            prefs[KEY_LOGGED_IN_USERNAME] = username
            prefs[KEY_LOGGED_IN_USER_NAME] = fullName
            if (studentId != null) {
                prefs[KEY_ACTIVE_STUDENT_ID] = studentId
            }
        }
    }

    suspend fun logout() {
        context.dataStore.edit { prefs ->
            prefs[KEY_IS_LOGGED_IN] = false
            prefs[KEY_LOGGED_IN_USERNAME] = ""
            prefs[KEY_LOGGED_IN_USER_NAME] = ""
        }
    }
}
