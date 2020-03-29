package pl.manager

import org.apache.http.client.HttpResponseException
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import pl.config.FacebookProperties
import pl.entity.Message
import pl.entity.MessageRequest
import pl.entity.Recipient

internal class MessagesManagerTest {
    private val facebookProperties: FacebookProperties = FacebookProperties(apiUrl = "http://facebook/send")
    private val restTemplate: RestTemplate = mock(RestTemplate::class.java)
    private val messagesManager = MessagesManager(restTemplate, facebookProperties)

    @Test
    fun `Should send message via facebook api`() {
        // given
        val accessToken = "access_token"
        val messageRequest = MessageRequest(
            recipient = Recipient("recipient_id"), message = Message("message content")
        )
        `when`(
            restTemplate.postForEntity(
                eq("http://facebook/send?access_token=$accessToken"), eq(messageRequest), eq(Any::class.java)
            )
        ).thenReturn(
            ResponseEntity(
                "Success", HttpStatus.OK
            )
        )

        // when
        val sendMessageResponse = messagesManager.sendMessage(accessToken, messageRequest)

        // then
        assertThat(sendMessageResponse).isEqualTo("Success")
    }

    @Test
    fun `Should throw exception when response status code is not 2xx`() {
        // given
        val accessToken = "token"
        val messageRequest = MessageRequest(
            recipient = Recipient("recipient_id"), message = Message("message content")
        )
        `when`(
            restTemplate.postForEntity(
                eq("http://facebook/send?access_token=$accessToken"), eq(messageRequest), eq(Any::class.java)
            )
        ).thenReturn(
            ResponseEntity(
                "Invalid message request", HttpStatus.BAD_REQUEST
            )
        )

        // then
        Assertions.assertThatExceptionOfType(HttpResponseException::class.java).isThrownBy { messagesManager.sendMessage(accessToken, messageRequest) }
            .withMessageStartingWith("Problems with sending message to recipient_id: Invalid message request")
    }

    @Test
    fun `Should throw exception when facebook api url not found`() {
        // given
        val accessToken = "token"
        val messageRequest = MessageRequest(
            recipient = Recipient("recipient_id"), message = Message("message content")
        )
        facebookProperties.apiUrl = null

        // then
        Assertions.assertThatExceptionOfType(Exception::class.java).isThrownBy { messagesManager.sendMessage(accessToken, messageRequest) }
            .withMessageStartingWith("Facebook API url not found")
    }
}
