package pl.translation

import org.springframework.stereotype.Component
import pl.logger

@Component
class Translator {
    fun mapToEnglishCountry(countryOriginal: String): String? {
        val countryOriginalLowerCase = countryOriginal.toLowerCase()
        logger.info("Mapping country $countryOriginal")
        return when (countryOriginalLowerCase) {
            "chiny" -> "china"
            "chinach" -> "china"
            "polska" -> "poland"
            "polsce" -> "poland"
            "włochy" -> "italy"
            "włoszech" -> "italy"
            "hiszpania" -> "spain"
            "niemcy" -> "germany"
            "francja" -> "france"
            "japonia" -> "japan"
            "czechy" -> "czechia"
            "usa" -> "usa"
            "stany zjednoczone" -> "usa"
            "ameryka" -> "usa"
            "iran" -> "iran"
            "szwajcaria" -> "switzerland"
            "anglia" -> "uk"
            "wielka brytania" -> "uk"
            "holandia" -> "netherlands"
            "austria" -> "austria"
            "belgia" -> "belgium"
            "kanada" -> "canada"
            "portugalia" -> "portugal"
            "szwecja" -> "sweden"
            "australia" -> "australia"
            "brazylia" -> "brazil"
            "turcja" -> "turkey"
            "rosja" -> "russia"
            else -> null
        }
    }

    companion object {
        private val logger by logger()
    }
}
