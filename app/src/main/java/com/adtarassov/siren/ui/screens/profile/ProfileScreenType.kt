package com.adtarassov.siren.ui.screens.profile

sealed interface ProfileScreenType {
  object Main : ProfileScreenType
  data class Other(val profileName: String) : ProfileScreenType
}