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
package com.afollestad.nocknock.utilities

import com.afollestad.nocknock.utilities.Qualifiers.IO_DISPATCHER
import com.afollestad.nocknock.utilities.Qualifiers.MAIN_DISPATCHER
import com.afollestad.nocknock.utilities.providers.RealStringProvider
import com.afollestad.nocknock.utilities.providers.StringProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.StringQualifier
import org.koin.dsl.bind
import org.koin.dsl.module

object Qualifiers {
  const val MAIN_DISPATCHER = "main_dispatcher"
  const val IO_DISPATCHER = "io_dispatcher"
}

/** @author Aidan Follestad (@afollestad) */
val commonModule = module {

  factory<CoroutineDispatcher>(StringQualifier(MAIN_DISPATCHER)) { Dispatchers.Main }

  factory(StringQualifier(IO_DISPATCHER)) { Dispatchers.IO }

  factory { RealStringProvider(get()) } bind StringProvider::class
}
