package com.bshpanchuk.testyalantis.presentation.model.mapper

import com.bshpanchuk.testyalantis.util.TestResourceManager
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import java.lang.reflect.Method

class FormatNumbersTest {
    private lateinit var presentationMapper: PresentationMapper

    private lateinit var method: Method
    private lateinit var parameters: Array<Int?>

    @Before
    fun setUp() {
        presentationMapper = PresentationMapper(TestResourceManager())

        method = presentationMapper.javaClass.getDeclaredMethod("formatNumbers", Int::class.java)
        method.isAccessible = true

        parameters = arrayOfNulls<Int>(1)
    }

    @Test
    fun moreThan1000_success() {
        parameters[0] = 12345

        val actual = method.invoke(presentationMapper, *parameters)
        assertThat(actual).isEqualTo("12.3k")
    }

    @Test
    fun lessThan1000_success() {
        parameters[0] = 123

        val actual = method.invoke(presentationMapper, *parameters)
        assertThat(actual).isEqualTo("123")
    }
    @Test
    fun lessThan0_success() {
        parameters[0] = -123

        val actual = method.invoke(presentationMapper, *parameters)
        assertThat(actual).isEqualTo("0")
    }


}