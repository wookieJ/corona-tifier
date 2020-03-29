package pl.translation

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class TranslatorTest {

    private val translator = Translator()

    @Test
    fun `Should map country name`() {
        // given
        val countries: List<String> = listOf(
            "chiny", "chinach", "polska", "polsce", "włochy", "włoszech", "hiszpania", "niemcy", "francja", "japonia", "czechy", "usa", "stany zjednoczone",
            "ameryka", "iran", "szwajcaria", "anglia", "wielka brytania", "holandia", "austria", "belgia", "kanada", "portugalia", "szwecja", "australia",
            "brazylia", "turcja", "rosja"
        )

        // when
        val translations = countries.map { translator.mapToEnglishCountry(it) }.toList()

        // then
        assertThat(translations[0]).isEqualTo("china")
        assertThat(translations[1]).isEqualTo("china")
        assertThat(translations[2]).isEqualTo("poland")
        assertThat(translations[3]).isEqualTo("poland")
        assertThat(translations[4]).isEqualTo("italy")
        assertThat(translations[5]).isEqualTo("italy")
        assertThat(translations[6]).isEqualTo("spain")
        assertThat(translations[7]).isEqualTo("germany")
        assertThat(translations[8]).isEqualTo("france")
        assertThat(translations[9]).isEqualTo("japan")
        assertThat(translations[10]).isEqualTo("czechia")
        assertThat(translations[11]).isEqualTo("usa")
        assertThat(translations[12]).isEqualTo("usa")
        assertThat(translations[13]).isEqualTo("usa")
        assertThat(translations[14]).isEqualTo("iran")
        assertThat(translations[15]).isEqualTo("switzerland")
        assertThat(translations[16]).isEqualTo("uk")
        assertThat(translations[17]).isEqualTo("uk")
        assertThat(translations[18]).isEqualTo("netherlands")
        assertThat(translations[19]).isEqualTo("austria")
        assertThat(translations[20]).isEqualTo("belgium")
        assertThat(translations[21]).isEqualTo("canada")
        assertThat(translations[22]).isEqualTo("portugal")
        assertThat(translations[23]).isEqualTo("sweden")
        assertThat(translations[24]).isEqualTo("australia")
        assertThat(translations[25]).isEqualTo("brazil")
        assertThat(translations[26]).isEqualTo("turkey")
        assertThat(translations[27]).isEqualTo("russia")
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
