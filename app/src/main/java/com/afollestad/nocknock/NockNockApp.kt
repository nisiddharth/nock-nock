/**
 * Designed and developed by Aidan Follestad (@afollestad)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:Suppress("unused")

package com.afollestad.nocknock

import android.app.Application
import com.afollestad.nocknock.BuildConfig.DEBUG
import com.afollestad.nocknock.engine.engineModule
import com.afollestad.nocknock.koin.mainModule
import com.afollestad.nocknock.koin.prefModule
import com.afollestad.nocknock.koin.viewModelModule
import com.afollestad.nocknock.logging.FabricTree
import com.afollestad.nocknock.notifications.NockNotificationManager
import com.afollestad.nocknock.notifications.notificationsModule
import com.afollestad.nocknock.utilities.commonModule
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree
import timber.log.Timber.d as log

/** @author Aidan Follestad (@afollestad) */
class NockNockApp : Application() {

  private var resumedActivities: Int = 0

  override fun onCreate() {
    super.onCreate()

    if (DEBUG) {
      Timber.plant(DebugTree())
    }

    Timber.plant(FabricTree())
    Fabric.with(this, Crashlytics())

    val modules = listOf(
        prefModule,
        mainModule,
        engineModule,
        commonModule,
        notificationsModule,
        viewModelModule
    )
    startKoin {
        this.androidContext(this@NockNockApp)
        this.modules(modules)
    }

      val nockNotificationManager by inject<NockNotificationManager>()
    onActivityLifeChange { activity, resumed ->
      if (resumed) {
        resumedActivities++
        log("Activity resumed: $activity, resumedActivities = $resumedActivities")
      } else {
        resumedActivities--
        log("Activity paused: $activity, resumedActivities = $resumedActivities")
      }
      check(resumedActivities >= 0) { "resumedActivities can't go below 0." }
      nockNotificationManager.setIsAppOpen(resumedActivities > 0)
    }
  }
}
