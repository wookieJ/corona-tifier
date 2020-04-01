package pl.stubs

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson
import static com.github.tomakehurst.wiremock.client.WireMock.post
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import static org.springframework.http.HttpStatus.OK

class MessagesStubs {
    static def sendMessageWithSuccessStub(def recipientId, def messageText, def responseMessageId) {
        stubFor(post(urlEqualTo("/v6.0/me/messages?access_token=accessToken"))
                .withRequestBody(equalToJson("""{
                            |    "recipient": {
                            |        "id": "${recipientId}"
                            |    },
                            |    "message": {
                            |        "text": "${messageText}"
                            |    }
                            |}""".stripMargin()))
                .willReturn(
                aResponse()
                        .withStatus(OK.value())
                        .withHeader("Content-type", "application/json")
                        .withBody(MessagesResponses.succesfullySentMessageResponse(recipientId, responseMessageId))
        ))
    }
}
