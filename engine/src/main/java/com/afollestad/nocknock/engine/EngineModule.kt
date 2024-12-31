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
package com.afollestad.nocknock.engine

import com.afollestad.nocknock.engine.ssl.RealSslManager
import com.afollestad.nocknock.engine.ssl.SslManager
import com.afollestad.nocknock.engine.validation.RealValidationExecutor
import com.afollestad.nocknock.engine.validation.ValidationExecutor
import org.koin.dsl.bind
import org.koin.dsl.module

/** @author Aidan Follestad (@afollestad) */
val engineModule = module {

  single {
    RealValidationExecutor(get(), get(), get(), get(), get(), get(), get())
  } bind ValidationExecutor::class

  factory { RealSslManager(get()) } bind SslManager::class
}
