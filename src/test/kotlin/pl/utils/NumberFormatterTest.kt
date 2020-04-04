package pl.utils

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class NumberFormatterTest {
    @Test
    fun `Should format to percentage`() {
        // given
        val numberFormatter = NumberFormatter()
        val numbers: List<Double> = listOf(0.23, 0.1234, 1.0, 1.2, 1.005)

        // when
        val percentages: List<String> = numbers.map { numberFormatter.toPercentage(it, 2) }

        // then
        assertThat(percentages).isEqualTo(listOf("23%", "12.34%", "100%", "120%", "100.5%"))
    }

    @Test
    fun `Should format long number`() {
        // given
        val numberFormatter = NumberFormatter()
        val numbers: List<Long> = listOf(100, 1200, 10000, 100000, 1000000, 10000000)

        // when
        val longNumbers: List<String> = numbers.map { numberFormatter.formatLong(it) }

        // then
        assertThat(longNumbers).isEqualTo(listOf("100", "1,200", "10,000", "100,000", "1,000,000", "10,000,000"))
    }
}
