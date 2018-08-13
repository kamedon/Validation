package com.kamedon.validation

import org.junit.Assert
import org.junit.Test

class ValidationsTest {
    class User(val name: String, val age: Int)
    class Account(val name: String) {
        val valid by ValidationProperty()
    }

    @Test
    fun syntaxText() {
        Validations(
                Validation<User> {
                    "name"{
                        be { name.length >= 5 } not "name: 5 characters or more"
                        be { name.length <= 10 } not "name: 10 characters or less"
                    }
                    "age"{
                        be { age >= 20 } not "age: Over 20 years old"
                    }
                }
        )
        Assert.assertTrue(true)
    }
}