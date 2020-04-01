package pl.stubs

class CasesResponses {

    static String countriesResponses() {
        """
        [
            {
                "country": "USA",
                "cases": 145542,
                "todayCases": 2051,
                "deaths": 2616,
                "todayDeaths": 33,
                "recovered": 4579
            },
            {
                "country": "Spain",
                "cases": 85195,
                "todayCases": 5085,
                "deaths": 7340,
                "todayDeaths": 537,
                "recovered": 16780           
            }
        ]
        """
    }

    static String polandCasesResponse() {
        """
        {
            "country": "Poland",
            "cases": 1984,
            "todayCases": 122,
            "deaths": 26,
            "todayDeaths": 4,
            "recovered": 7
        }
        """
    }
}
