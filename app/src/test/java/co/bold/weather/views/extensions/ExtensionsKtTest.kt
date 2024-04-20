package co.bold.weather.views.extensions

import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date

class ExtensionsKtTest {
    @Test
    fun testToCurrentDayFormat() {
        val dateString = "2024-04-20 15:30"
        val expected = "Saturday, 03:30 PM"

        val inputFormat = mockk<SimpleDateFormat>()
        val outputFormat = mockk<SimpleDateFormat>()
        val inputDate = mockk<Date>()

        every { inputFormat.parse(dateString) } returns inputDate
        every { outputFormat.format(inputDate) } returns expected

        val result = dateString.toCurrentDayFormat()
        assertEquals(expected, result)
    }

    @Test
    fun testToCurrentDayNameFormat() {
        val dateString = "2024-04-20"
        val expected = "Saturday"

        val inputFormat = mockk<SimpleDateFormat>()
        val outputFormat = mockk<SimpleDateFormat>()
        val inputDate = mockk<Date>()

        every { inputFormat.parse(dateString) } returns inputDate
        every { outputFormat.format(inputDate) } returns expected

        val result = dateString.toCurrentDayNameFormat()
        assertEquals(expected, result)
    }
}