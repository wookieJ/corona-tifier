package pl.translation

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class TranslatorTest {

    private val translator = Translator()

    @Test
    fun `Should map country name`() {
        // given
        val countries: List<String> = listOf(
            "chiny", "polska", "włochy", "hiszpania", "niemcy", "francja", "japonia", "czechy", "stany zjednoczone", "iran", "szwajcaria", "wielka brytania",
            "holandia", "austria", "belgia", "kanada", "portugalia", "szwecja", "australia", "brazylia", "turcja", "rosja"
        )

        // when
        val translations = countries.map { translator.mapToEnglishCountry(it) }.toList()

        // then
        assertThat(translations).isEqualTo(
            listOf(
                "china", "poland", "italy", "spain", "germany", "france", "japan", "czech republic", "united states", "iran", "switzerland", "united kingdom",
                "netherlands", "austria", "belgium", "canada", "portugal", "sweden", "australia", "brazil", "turkey", "russia"
            )
        )
    }

    @Test
    fun `Should return null if country not found`() {
        // given
        val name = "NotExistingCountry"

        // when
        val translatedName = translator.mapToEnglishCountry(name)

        // then
        assertThat(translatedName).isNull()
    }
}
