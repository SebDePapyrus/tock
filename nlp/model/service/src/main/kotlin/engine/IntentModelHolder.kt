/*
 * Copyright (C) 2017/2019 e-voyageurs technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ai.tock.nlp.model.service.engine

import ai.tock.nlp.core.Application
import ai.tock.nlp.model.ModelHolder
import ai.tock.nlp.core.configuration.NlpApplicationConfiguration
import java.time.Instant
import java.time.Instant.now

/**
 * Holder of native intent model.
 */
data class IntentModelHolder(
    val application: Application,
    val nativeModel: Any,
    val configuration: NlpApplicationConfiguration,
    val lastUpdate: Instant = now()
) : ModelHolder
