package pl.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import pl.WireMockIntegrationSpec
import pl.entity.Context
import pl.entity.Intent
import pl.entity.QueryResult
import pl.entity.WebhookRequest
import pl.stubs.CasesResponses
import pl.stubs.CasesStubs
import pl.stubs.JokesResponses
import pl.stubs.JokesStubs
import pl.stubs.MessagesStubs
import pl.webhook.CountryWebhookHandler
import pl.webhook.DefaultWebhookHandler
import pl.webhook.WebhookCommonExtractor

class WebhookControllerIntegrationTest extends WireMockIntegrationSpec {

    @Autowired
    WebhookController webhookController

    /**
     * Thread.sleep(2000) due to:
     *  https://hc.apache.org/httpcomponents-client-ga/httpclient/apidocs/org/apache/http/impl/conn/PoolingHttpClientConnectionManager.html
     */
    def setup() {
        Thread.sleep(2000)
        System.setProperty(WebhookController.ACCESS_TOKEN_ENV_NAME, "accessToken")
    }

    def cleanup() {
        System.clearProperty(WebhookController.ACCESS_TOKEN_ENV_NAME)
    }

    def "Should send message with Poland country cases"() {
        given:
        def countryCasesMessage = "Liczba przypadków : *1984*\\nLiczba śmierci : *26*\\nDzisiaj przybyło : *122*\\n" +
                "Dzisiaj zmarło : *4*\\nUzdrowionych : *7*"
        CasesStubs.getCountryStub("poland", CasesResponses.polandCasesResponse())
        MessagesStubs.sendMessageWithSuccessStub("recipient_id", countryCasesMessage, "message_id")
        def queryResult = [
                outputContexts: [
                        [
                                name      : WebhookCommonExtractor.CONTEXT_NAME,
                                parameters: [
                                        (WebhookCommonExtractor.RECIPIENT_ID_PARAMETER_NAME): "recipient_id"
                                ]
                        ] as Context
                ],
                parameters    : [
                        (CountryWebhookHandler.COUNTRY_PARAMETER_NAME): "Polska"
                ],
                intent        : [
                        displayName: "country cases"
                ] as Intent
        ] as QueryResult
        def webhookRequest = new WebhookRequest(null, queryResult, null, null)

        when:
        def response = webhookController.webHookEvent(webhookRequest)

        then:
        response.statusCode == HttpStatus.OK
        response.body["recipient_id"] == "recipient_id"
        response.body["message_id"] == "message_id"
    }

    def "Should send message with random joke"() {
        given:
        def jokeMessage = "Knock! Knock! Who’s there? Scold. Scold who? Scold outside, let me in!"
        JokesStubs.getRandomJokeStub(JokesResponses.randomJokeHtml())
        MessagesStubs.sendMessageWithSuccessStub("recipient_id", jokeMessage, "message_id")
        def queryResult = [
                outputContexts: [
                        [
                                name      : WebhookCommonExtractor.CONTEXT_NAME,
                                parameters: [
                                        (WebhookCommonExtractor.RECIPIENT_ID_PARAMETER_NAME): "recipient_id"
                                ]
                        ] as Context
                ],
                intent        : [
                        displayName: "joke"
                ] as Intent
        ] as QueryResult
        def webhookRequest = new WebhookRequest(null, queryResult, null, null)

        when:
        def response = webhookController.webHookEvent(webhookRequest)

        then:
        response.statusCode == HttpStatus.OK
        response.body["recipient_id"] == "recipient_id"
        response.body["message_id"] == "message_id"
    }

    def "Should send default message if intent doesn't match"() {
        given:
        MessagesStubs.sendMessageWithSuccessStub("recipient_id", DefaultWebhookHandler.DEFAULT_MESSAGE, "message_id")
        def queryResult = [
                outputContexts: [
                        [
                                name      : WebhookCommonExtractor.CONTEXT_NAME,
                                parameters: [
                                        (WebhookCommonExtractor.RECIPIENT_ID_PARAMETER_NAME): "recipient_id"
                                ]
                        ] as Context
                ],
                intent        : [
                        displayName: "NotRecognizedIntent"
                ] as Intent
        ] as QueryResult
        def webhookRequest = new WebhookRequest(null, queryResult, null, null)

        when:
        def response = webhookController.webHookEvent(webhookRequest)

        then:
        response.statusCode == HttpStatus.OK
        response.body["recipient_id"] == "recipient_id"
        response.body["message_id"] == "message_id"
    }

    def "Should throw error while access token environment variable not found"() {
        given:
        System.clearProperty(WebhookController.ACCESS_TOKEN_ENV_NAME)

        when:
        webhookController.webHookEvent(new WebhookRequest(null, null, null, null))

        then:
        thrown(Exception)
    }

    def "Should throw exception if query result not found"() {
        given:
        def webhookRequest = new WebhookRequest(null, null, null, null)

        when:
        webhookController.webHookEvent(webhookRequest)

        then:
        thrown(Exception)
    }

    def "Should throw exception if intent not found"() {
        given:
        MessagesStubs.sendMessageWithSuccessStub("recipient_id", DefaultWebhookHandler.DEFAULT_MESSAGE, "message_id")
        def queryResult = [] as QueryResult
        def webhookRequest = new WebhookRequest(null, queryResult, null, null)

        when:
        webhookController.webHookEvent(webhookRequest)

        then:
        thrown(Exception)
    }

    def "Should throw exception if display name not found"() {
        given:
        MessagesStubs.sendMessageWithSuccessStub("recipient_id", DefaultWebhookHandler.DEFAULT_MESSAGE, "message_id")
        def queryResult = [
                intent: [] as Intent
        ] as QueryResult
        def webhookRequest = new WebhookRequest(null, queryResult, null, null)

        when:
        webhookController.webHookEvent(webhookRequest)

        then:
        thrown(Exception)
    }

    def "Should throw exception if display name is null"() {
        given:
        MessagesStubs.sendMessageWithSuccessStub("recipient_id", DefaultWebhookHandler.DEFAULT_MESSAGE, "message_id")
        def queryResult = [
                intent: [
                        displayName: null
                ] as Intent
        ] as QueryResult
        def webhookRequest = new WebhookRequest(null, queryResult, null, null)

        when:
        webhookController.webHookEvent(webhookRequest)

        then:
        thrown(Exception)
    }

    def "Should throw exception if display name is empty"() {
        given:
        MessagesStubs.sendMessageWithSuccessStub("recipient_id", DefaultWebhookHandler.DEFAULT_MESSAGE, "message_id")
        def queryResult = [
                intent: [
                        displayName: ""
                ] as Intent
        ] as QueryResult
        def webhookRequest = new WebhookRequest(null, queryResult, null, null)

        when:
        webhookController.webHookEvent(webhookRequest)

        then:
        thrown(Exception)
    }
}
