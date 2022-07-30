package com.adtarassov.siren.data

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
      getTokenFromPref()
    )
  )

  fun userAuthModelFlow(): StateFlow<UserAuthModel> = userAuthModel

  fun authorizeUser(token: String?) {
    prefs.edit()
      .putString(TOKEN_KEY, token)
      .apply()

    externalScope.launch {
      val newModel = userAuthModelFlow().value.copy(token = token)
      userAuthModel.emit(newModel)
    }
  }

  private fun getTokenFromPref() = prefs.getString(TOKEN_KEY, null)

}

data class UserAuthModel(
  val token: String?,
)