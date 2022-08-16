package com.adtarassov.siren.ui.screens.profile

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
class ProfileScreenType(
  val profileName: String?,
  val screenType: Type,
) : Parcelable {

  companion object {
    const val KEY = "profile_transporting_model"

    object ProfileScreenNavType : NavType<ProfileScreenType>(isNullableAllowed = false) {
      override fun get(bundle: Bundle, key: String): ProfileScreenType? {
        return bundle.getParcelable(key)
      }

      override fun parseValue(value: String): ProfileScreenType {
        return Gson().fromJson(value, ProfileScreenType::class.java)
      }

      override fun put(bundle: Bundle, key: String, value: ProfileScreenType) {
        bundle.putParcelable(key, value)
      }
    }

  }

  enum class Type {
    MAIN, OTHER
  }
}