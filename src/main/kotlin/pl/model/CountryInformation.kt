package pl.model

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
        return """
            Liczba przypadków : ${this.cases}
            Liczba śmierci    : ${this.deaths}
            Dzisiaj przybyło  : ${this.todayCases}
            Dzisiaj zmarło    : ${this.todayDeaths}
            Uzdrowionych      : ${this.recovered}
            """.trimIndent()
    }
}
