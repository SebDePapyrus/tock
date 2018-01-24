/*
 * Copyright (C) 2017 VSCT
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

package fr.vsct.tock.bot.connector.alexa

import com.amazon.speech.json.SpeechletRequestEnvelope
import com.amazon.speech.slu.Intent
import com.amazon.speech.speechlet.IntentRequest
import com.amazon.speech.speechlet.LaunchRequest
import com.amazon.speech.speechlet.SessionEndedRequest
import com.amazon.speech.speechlet.SessionStartedRequest
import com.amazon.speech.speechlet.SpeechletResponse
import com.amazon.speech.speechlet.SpeechletV2
import com.amazon.speech.ui.PlainTextOutputSpeech
import com.amazon.speech.ui.Reprompt
import com.amazon.speech.ui.SsmlOutputSpeech
import fr.vsct.tock.bot.connector.ConnectorCallbackBase
import fr.vsct.tock.bot.connector.ConnectorData
import fr.vsct.tock.bot.engine.BotRepository
import fr.vsct.tock.bot.engine.ConnectorController
import fr.vsct.tock.bot.engine.action.Action
import fr.vsct.tock.bot.engine.action.SendSentence
import fr.vsct.tock.bot.engine.event.Event
import fr.vsct.tock.shared.error
import fr.vsct.tock.shared.jackson.mapper
import fr.vsct.tock.translator.isSSML
import io.vertx.ext.web.RoutingContext
import mu.KotlinLogging
import java.util.concurrent.CopyOnWriteArrayList

/**
 *
 */
internal data class AlexaConnectorCallback(
        override val applicationId: String,
        val controller: ConnectorController,
        val alexaTockMapper: AlexaTockMapper,
        val context: RoutingContext,
        val actions: MutableList<ActionWithDelay> = CopyOnWriteArrayList()
) : ConnectorCallbackBase(applicationId, alexaConnectorType), SpeechletV2 {

    @Volatile
    private var answered: Boolean = false
    @Volatile
    private var alexaResponse: SpeechletResponse? = null

    companion object {
        private val logger = KotlinLogging.logger {}
    }

    data class ActionWithDelay(val action: Action, val delayInMs: Long = 0)

    fun addAction(event: Event, delayInMs: Long) {
        if (event is Action) {
            actions.add(ActionWithDelay(event, delayInMs))
        } else {
            logger.trace { "unsupported event: $event" }
        }
    }

    fun buildResponse(): SpeechletResponse {
        val answer = actions.mapNotNull { it.action as? SendSentence }
                .mapNotNull { it.stringText }
                .joinToString(".")
        val speech = if (answer.isSSML()) SsmlOutputSpeech().apply { ssml = answer }
        else PlainTextOutputSpeech().apply { text = answer }
        return SpeechletResponse.newAskResponse(
                speech,
                Reprompt().apply { outputSpeech = speech }

        )
    }

    fun sendResponse() {
        try {
            if (!answered) {
                answered = true
                alexaResponse = buildResponse()

                logger.debug { "alexa response : $alexaResponse" }
            } else {
                logger.trace { "already answered: $this" }
            }
        } catch (t: Throwable) {
            logger.error(t)
            context.fail(t)
        }
    }

    override fun eventSkipped(event: Event) {
        super.eventSkipped(event)
        sendResponse()
    }

    override fun eventAnswered(event: Event) {
        super.eventAnswered(event)
        sendResponse()
    }

    override fun exceptionThrown(event: Event, throwable: Throwable) {
        super.exceptionThrown(event, throwable)
        sendTechnicalError(throwable)
    }

    fun sendTechnicalError(
            throwable: Throwable,
            request: IntentRequest? = null
    ) {
        try {
            //TODO
            logger.error(throwable)
            context.fail(throwable)
        } catch (t: Throwable) {
            logger.error(t)
            context.fail(t)
        }
    }

    private fun logRequest(method: String, req: SpeechletRequestEnvelope<*>) {
        logger.debug { "$method : \n${mapper.writeValueAsString(req.context)}\n${mapper.writeValueAsString(req.session)}\n${mapper.writeValueAsString(req.request)}" }
    }

    override fun onSessionStarted(requestEnvelope: SpeechletRequestEnvelope<SessionStartedRequest>) {
        logRequest("onSessionStarted", requestEnvelope)
    }

    override fun onSessionEnded(requestEnvelope: SpeechletRequestEnvelope<SessionEndedRequest>) {
        logRequest("onSessionEnded", requestEnvelope)
    }

    override fun onIntent(requestEnvelope: SpeechletRequestEnvelope<IntentRequest>): SpeechletResponse {
        logRequest("onIntent", requestEnvelope)

        val timerData = BotRepository.requestTimer.start("alexa_webhook")
        try {
            val event = alexaTockMapper.toEvent(requestEnvelope.session.user.userId, requestEnvelope.request, controller.botDefinition)
            controller.handle(event, ConnectorData(this))
        } catch (t: Throwable) {
            BotRepository.requestTimer.throwable(t, timerData)
            sendTechnicalError(t, requestEnvelope.request)
        } finally {
            BotRepository.requestTimer.end(timerData)
        }

        return alexaResponse!! //TODO
    }

    override fun onLaunch(requestEnvelope: SpeechletRequestEnvelope<LaunchRequest>): SpeechletResponse {
        logRequest("onLaunch", requestEnvelope)
        val helloStory = controller.botDefinition.run { helloStory ?: stories.first() }.mainIntent().name
        return onIntent(SpeechletRequestEnvelope
                .builder<IntentRequest>()
                .withContext(requestEnvelope.context)
                .withSession(requestEnvelope.session)
                .withVersion(requestEnvelope.version)
                .withRequest(
                        IntentRequest.builder()
                                .withRequestId(requestEnvelope.request.requestId)
                                .withTimestamp(requestEnvelope.request.timestamp)
                                .withLocale(requestEnvelope.request.locale)
                                .withIntent(Intent.builder().withName(helloStory).build())
                                .withDialogState(IntentRequest.DialogState.STARTED)
                                .build())
                .build()
        )
    }

}