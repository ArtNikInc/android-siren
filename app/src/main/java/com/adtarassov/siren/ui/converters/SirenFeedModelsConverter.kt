package com.adtarassov.siren.ui.converters

import android.content.Context
import android.content.res.XmlResourceParser
import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap
import com.adtarassov.siren.R
import com.adtarassov.siren.data.api.SirenFeedModel
import com.adtarassov.siren.ui.models.SirenFeedUiModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class SirenFeedModelsConverter @Inject constructor(
  @ApplicationContext
  private val context: Context,
) {

  suspend fun convertToFeedModel(sirenFeedModels: SirenFeedModel): SirenFeedUiModel {
    delay(5)
    return SirenFeedUiModel(
      id = sirenFeedModels.id,
      header = sirenFeedModels.header,
      body = sirenFeedModels.body,
      authorImage = getAuthorImageDrawable(sirenFeedModels.authorImageUrl),
      authorName = sirenFeedModels.authorName
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

    fun createTestFeedModel(): SirenFeedModel =
      SirenFeedModel(
        id = random.nextLong(),
        header = "header",
        body = "body",
        authorId = random.nextLong(),
        authorImageUrl = null,
        authorName = "authorName",
      )

    fun createLongTestFeedModel(): SirenFeedModel =
      SirenFeedModel(
        id = random.nextLong(),
        header = "header_header_header_header_header_header_header_header",
        body = "body_body_body_body_body_body_body_body_body_body_body_body_body_body_body_body_body_body_body_body",
        authorId = random.nextLong(),
        authorImageUrl = null,
        authorName = "authorName_authorName_authorName_authorName_authorName_authorName_authorName_authorName_author",
      )
  }

}