package com.kamedon.validation

import org.junit.Assert
import org.junit.Test

/**
 * Created by Kamedon
 */

class ValidationTest {
    class User(val name: String, val age: Int)

    val validation = Validation<User> {
        "name"{
            be { name.length >= 5 } not "name: 5 characters or more"
            be { name.length <= 10 } not "name: 10 characters or less"
        }
        "age"{
            be { age >= 20 } not error message { "age: Over 20 years old" }
            be { age <= 150 } not with callback ("age: Over 150 years old"){
            }
        }
    }

    @Test
    fun validDataTest() {
        val user = User("kamedon", 30)
        val expected = 0
        val actual = validation.validate(user).size
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun invalidNameLessTest() {
        val user = User("kame", 30)
        val errors = validation.validate(user)

        val expected = "name: 5 characters or more"
        val actual = errors["name"]!![0]
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun invalidNameMoreTest() {
        val user = User("kamedon3939393939", 30)
        val errors = validation.validate(user)

        val expected = "name: 10 characters or less"
        val actual = errors["name"]!![0]
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun invalidLessAgeTest() {
        val user = User("kamedon", 5)
        val errors = validation.validate(user)

        val expected = "age: Over 20 years old"
        val actual = errors["age"]!![0]
        Assert.assertEquals(expected, actual)
    }

    @Test
    fun invalidOverAgeTest() {
        val user = User("kamedon", 1000)
        var callback = false
        val v = Validation<User> {
            "name"{
                be { name.length >= 5 } not "name: 5 characters or more"
                be { name.length <= 10 } not "name: 10 characters or less"
            }
            "age"{
                be { age >= 20 } not error message { "age: Over 20 years old" }
                be { age <= 150 } not with callback ("age: Over 150 years old"){
                    callback = true
                }
            }
        }
        val errors = v.validate(user)

        var expectedCallback = true
        val expected = "age: Over 150 years old"
        val actual = errors["age"]!![0]
        Assert.assertEquals(expected, actual)

        Assert.assertEquals(expectedCallback, callback)
    }


    @Test
    fun invalidNameAndAgeTest() {
        val user = User("k", 0)

        // {name=[name: 5 characters or more], age=[age: Over 20 years old]}
        val errors = validation.validate(user)

        Assert.assertEquals(errors["name"]!![0], "name: 5 characters or more")
        Assert.assertEquals(errors["age"]!![0], "age: Over 20 years old")
        Assert.assertEquals(errors.size, 2)
    }


}
