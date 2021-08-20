package com.bshpanchuk.testyalantis.presentation.model.mapper

import com.bshpanchuk.testyalantis.util.TestResourceManager
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import java.lang.reflect.Method
import java.time.LocalDateTime
import java.time.ZoneOffset

class FormatDateTest {


    private lateinit var presentationMapper: PresentationMapper

    private lateinit var method: Method
    private lateinit var parameters: Array<Long?>

    @Before
    fun setUp() {
        presentationMapper = PresentationMapper(TestResourceManager())

        method = presentationMapper.javaClass.getDeclaredMethod("formatData", Long::class.java)
        method.isAccessible = true

        parameters = arrayOfNulls<Long>(1)
    }

    @Test
    fun formatDate1Month_success() {
        val dataPost: Long = LocalDateTime.now().minusMonths(1).getMillis() / 1000

        parameters[0] = dataPost

        val actual = method.invoke(presentationMapper, *parameters)

        Truth.assertThat(actual).isEqualTo("1 month ago")
    }

    @Test
    fun formatDate2Months_success() {
        val dataPost: Long = LocalDateTime.now().minusMonths(2).getMillis() / 1000

        parameters[0] = dataPost

        val actual = method.invoke(presentationMapper, *parameters)

        Truth.assertThat(actual).isEqualTo("2 months ago")
    }

}

private fun LocalDateTime.getMillis() : Long {
    return toInstant(ZoneOffset.ofTotalSeconds(0))
        .toEpochMilli()
}