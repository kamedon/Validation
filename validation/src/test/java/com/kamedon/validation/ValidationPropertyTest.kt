package com.kamedon.validation

import org.junit.Assert
import org.junit.Test


class ValidationPropertyTest {
    class Account(val name: String) {
        val valid by ValidationProperty()
    }

    @Test
    fun syntaxText() {
        val account = Account("kamedon")
        val expected = true
        val actual = account.valid
        Assert.assertEquals(expected, actual)
    }
}