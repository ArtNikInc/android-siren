package com.adtarassov.siren.ui.converters

import android.content.Context
import com.adtarassov.siren.data.api.SirenFeedModel
import com.adtarassov.siren.ui.models.SirenFeedUiModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SirenFeedModelsConverter @Inject constructor(
  @ApplicationContext
  private val context: Context,
) {

  suspend fun convertFeedModel(sirenFeedModels: SirenFeedModel): SirenFeedUiModel {
    delay(5)
    return SirenFeedUiModel(
      header = sirenFeedModels.header,
      body = sirenFeedModels.body,
      authorImage = null
    )
  }

  companion object {
    fun createTestFeedModel(): SirenFeedModel =
      SirenFeedModel(
        header = "header",
        body = "body",
        authorId = 123,
        authorImageUrl = null

      )
  }

}