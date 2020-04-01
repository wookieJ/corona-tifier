package pl.manager

import org.springframework.beans.factory.annotation.Autowired
import pl.WireMockIntegrationSpec
import pl.manager.JokesManager
import pl.stubs.JokesResponses
import pl.stubs.JokesStubs

class JokesIntegrationTest extends WireMockIntegrationSpec {

    @Autowired
    JokesManager jokesManager

    def "Should return random joke"() {
        given:
        JokesStubs.getRandomJokeStub(JokesResponses.randomJokeHtml())

        when:
        def joke = jokesManager.getRandomJoke()

        then:
        joke == "Knock! Knock! Who’s there? Scold. Scold who? Scold outside, let me in!"
    }

    def "Should return message joke not found"() {
        given:
        JokesStubs.getRandomJokeStub(JokesResponses.emptyJokeHtml())

        when:
        jokesManager.getRandomJoke()

        then:
        thrown(Exception)
    }
}
