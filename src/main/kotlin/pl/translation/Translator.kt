package pl.translation

import org.springframework.stereotype.Component
import pl.logger
import java.util.Locale


@Component
class Translator {
    fun mapToEnglishCountry(countryOriginal: String): String? {
        val countryOriginalLowerCase = countryOriginal.toLowerCase()
        logger.info("Mapping country $countryOriginal")
        val outLocale = Locale.forLanguageTag("en_GB")
        val inLocale = Locale.forLanguageTag("pl-PL")
        for (local in Locale.getAvailableLocales()) {
            val display = local.getDisplayCountry(inLocale).toLowerCase()
            if (display == countryOriginalLowerCase) {
                return local.getDisplayCountry(outLocale).toLowerCase()
            }
        }
        return null
    }

    companion object {
        private val logger by logger()
    }
}
