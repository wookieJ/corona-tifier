package pl.model

import pl.utils.NumberFormatter

data class CountryInformation(
    var country: String,
    var cases: Long,
    var todayCases: Long,
    var deaths: Long,
    var todayDeaths: Long,
    var recovered: Long,
    var critical: Long
) {
    fun toHumanizedFormat(): String{
        val numberFormatter = NumberFormatter()
        val mortality = this.deaths.toDouble() / (this.deaths + this.recovered)
        return """
            Liczba przypadków : ${numberFormatter.formatLong(this.cases)}
            Liczba śmierci : ${numberFormatter.formatLong(this.deaths)}
            Dzisiaj przybyło : ${numberFormatter.formatLong(this.todayCases)}
            Dzisiaj zmarło : ${numberFormatter.formatLong(this.todayDeaths)}
            Uzdrowionych : ${numberFormatter.formatLong(this.recovered)}
            Śmiertelność : ${numberFormatter.toPercentage(mortality, 0)}
            """.trimIndent()
    }
}
