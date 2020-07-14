package com.almissbah.weather.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class UserInputValidatorTest {
    private val query1 = "Dubai, Abu Dhabi"
    private val query2 = "Dubai, Abu Dhabi,"
    private val query3 = "Dubai, Abu Dhabi,Sharijah"
    private val query4 = "Dubai, Abu Dhabi,,,,,"
    private val query5 = "Dubai, Abu Dhabi,Dubai,Dubai,Dubai,Dubai,Dubai,Dubai"
    private val query6 = "Dubai, Abu Dhabi,City1,City2,City3,City4,City5,City6"

    @Test
    fun testValidateCitiesQuery() {
        assertEquals(
            UserInputValidator.CitiesCount.LessThanThreeCities,
            UserInputValidator.validateCitiesQuery(query1)
        )
        assertEquals(
            UserInputValidator.CitiesCount.LessThanThreeCities,
            UserInputValidator.validateCitiesQuery(query2)
        )
        assertEquals(
            UserInputValidator.CitiesCount.Valid,
            UserInputValidator.validateCitiesQuery(query3)
        )

        assertEquals(
            UserInputValidator.CitiesCount.LessThanThreeCities,
            UserInputValidator.validateCitiesQuery(query4)
        )


        assertEquals(
            UserInputValidator.CitiesCount.LessThanThreeCities,
            UserInputValidator.validateCitiesQuery(query5)
        )

        assertEquals(
            UserInputValidator.CitiesCount.MoreThanSevenCities,
            UserInputValidator.validateCitiesQuery(query6)
        )

    }

    @Test
    fun testValidateCitiesCount() {
        assertEquals(
            UserInputValidator.CitiesCount.LessThanThreeCities,
            UserInputValidator.validateCitiesCount(1)
        )
        assertEquals(
            UserInputValidator.CitiesCount.LessThanThreeCities,
            UserInputValidator.validateCitiesCount(2)
        )
        assertEquals(
            UserInputValidator.CitiesCount.Valid,
            UserInputValidator.validateCitiesCount(3)
        )
        assertEquals(
            UserInputValidator.CitiesCount.Valid,
            UserInputValidator.validateCitiesCount(4)
        )
        assertEquals(
            UserInputValidator.CitiesCount.Valid,
            UserInputValidator.validateCitiesCount(5)
        )
        assertEquals(
            UserInputValidator.CitiesCount.Valid,
            UserInputValidator.validateCitiesCount(6)
        )
        assertEquals(
            UserInputValidator.CitiesCount.Valid,
            UserInputValidator.validateCitiesCount(7)
        )
        assertEquals(
            UserInputValidator.CitiesCount.MoreThanSevenCities,
            UserInputValidator.validateCitiesCount(8)
        )

        assertEquals(
            UserInputValidator.CitiesCount.MoreThanSevenCities,
            UserInputValidator.validateCitiesCount(80)
        )
    }


    @Test
    fun testGetValidateCityList() {
        assertEquals(
            2,
            UserInputValidator.getValidCityList(query4).size
        )
        assertEquals(
            "Dubai",
            UserInputValidator.getValidCityList(query4)[0]
        )

        assertEquals(
            "Abu dhabi",
            UserInputValidator.getValidCityList(query4)[1]
        )

    }
}