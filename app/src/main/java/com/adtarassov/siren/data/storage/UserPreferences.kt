package com.adtarassov.siren.data.storage

import android.content.Context
import androidx.preference.PreferenceManager
import com.adtarassov.siren.di.ApplicationScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

private const val TOKEN_KEY = "token_key"
private const val USER_NAME_KEY = "user_name_key"

@Singleton
class UserPreferences @Inject constructor(
  @ApplicationContext
  context: Context,
  @ApplicationScope
  private val externalScope: CoroutineScope,
) {
  private val prefs = PreferenceManager.getDefaultSharedPreferences(context)

  private val userAuthModel: MutableStateFlow<UserAuthModel> = MutableStateFlow(
    UserAuthModel(
      getTokenFromPref(),
      getUserNameFromPref()
    )
  )

  fun userAuthModelFlow(): StateFlow<UserAuthModel> = userAuthModel

  fun authorizeUser(token: String, userName: String) {
    saveAuthorizeUserState(token, userName)
  }

  private fun saveAuthorizeUserState(token: String?, userName: String?) {
    prefs.edit()
      .putString(TOKEN_KEY, token)
      .putString(USER_NAME_KEY, userName)
      .apply()

    externalScope.launch {
      val newModel = UserAuthModel(token, userName)
      userAuthModel.emit(newModel)
    }
  }

  fun logoutUser() {
    saveAuthorizeUserState(null, null)
  }

  private fun getTokenFromPref() = prefs.getString(TOKEN_KEY, null)

  private fun getUserNameFromPref() = prefs.getString(USER_NAME_KEY, null)

}

data class UserAuthModel(
  val token: String?,
  val userName: String?
) {
  fun isAuth() = token != null && !userName.isNullOrBlank()
}