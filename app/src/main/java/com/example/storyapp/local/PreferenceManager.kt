import android.content.Context
import androidx.core.content.edit
import com.example.storyapp.api.LoginResult

object PreferenceManager {
    private const val PREF_NAME = "MyAppPrefs"
    private const val PREF_TOKEN = "token"

    fun saveToken(context: Context, token: String) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit {
            putString(PREF_TOKEN, token)
        }
    }

    fun getToken(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return sharedPreferences.getString(PREF_TOKEN, null)
    }

    fun clearToken(context: Context) {
        val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        sharedPreferences.edit {
            remove(PREF_TOKEN)
        }
    }

    fun isLoggedIn(context: Context): Boolean {
        val token = getToken(context)
        return !token.isNullOrBlank()
    }
}
