package com.adtarassov.siren.ui.converters

import android.content.Context
import android.graphics.drawable.Drawable
import com.adtarassov.siren.R
import com.adtarassov.siren.data.api.ProfileModel
import com.adtarassov.siren.data.api.ProfileStatistic
import com.adtarassov.siren.data.api.SirenFeedModel
import com.adtarassov.siren.ui.models.ProfileUiModel
import com.adtarassov.siren.ui.models.ProfileUiModel.Statistic
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class SirenProfileModelsConverter @Inject constructor(
  @ApplicationContext
  private val context: Context,
) {

  suspend fun convertToProfileUiModel(profileModel: ProfileModel): ProfileUiModel {
    delay(5)
    return ProfileUiModel(
      id = profileModel.id,
      name = profileModel.name,
      description = profileModel.description,
      profileIcon = getAuthorImageDrawable(profileModel.imageUrl),
      statistic = Statistic(
        views = profileModel.statistic.views,
        likes = profileModel.statistic.likes,
        followers = profileModel.statistic.followers
      )
    )
  }

  private fun getAuthorImageDrawable(authorImageUrl: String?): Drawable? {
    if (authorImageUrl == null) {
      return context.getDrawable(R.drawable.ic_baseline_account_circle_24)
    }
    return context.getDrawable(R.drawable.ic_baseline_account_circle_24)
  }

  companion object {
    private val random = Random(1)

    fun createTestProfileModel(): ProfileModel =
      ProfileModel(
        id = random.nextLong(),
        name = "name",
        imageUrl = null,
        description = "description",
        statistic = ProfileStatistic(
          views = 0,
          likes = 0,
          followers = 0
        )
      )

    fun createLongTestProfileModel(): ProfileModel =
      ProfileModel(
        id = random.nextLong(),
        name = "name_name_name_name_name_name_name_name_name_name_name_name_name_name_name_name_name_name_name",
        imageUrl = null,
        description = "description_description_description_description_description",
        statistic = ProfileStatistic(
          views = random.nextLong(0, 100),
          likes = random.nextLong(0, 100),
          followers = random.nextLong(0, 100)
        )
      )
  }

}