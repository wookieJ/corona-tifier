package pl.model

data class CountryInformation(
    var country: String,
    var cases: Long,
    var todayCases: Long,
    var deaths: Long,
    var todayDeaths: Long,
    var recovered: Long,
    var critical: Long
)
