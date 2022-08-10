package com.adtarassov.siren.data

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import com.adtarassov.siren.di.ApplicationScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Deprecated("to be continued")
@Singleton
class AudioPlayerManager @Inject constructor(
  @ApplicationContext
  private val context: Context,
  @ApplicationScope
  private val externalScope: CoroutineScope,
) {
  private val audioService: MutableStateFlow<Any?> = MutableStateFlow(null)
  fun audioServiceValue() = audioService.value

  private val connection = object : ServiceConnection {
    override fun onServiceConnected(className: ComponentName, service: IBinder) {
//      val binder = service as AudioService.AudioBinder
//      externalScope.launch {
//        audioService.emit(binder.getService())
//      }
    }

    override fun onServiceDisconnected(className: ComponentName) {
      externalScope.launch {
        audioService.emit(null)
      }
    }
  }

//  init {
//    Intent(context, AudioService::class.java).also { intent ->
//      context.bindService(intent, connection, Context.BIND_AUTO_CREATE)
//    }
//  }

}