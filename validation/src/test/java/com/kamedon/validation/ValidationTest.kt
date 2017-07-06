package com.kamedon.validation

import com.taroid.knit.should
import org.junit.Test

/**
 * Created by Kamedon
 */
class ValidationTest {

    @Test
    fun validate_1Test() {
        val validation = Validation<User> {
            be { name.length >= 5 } not "name: 5 characters or more"
            be { age >= 20 } not "age: Over 20 years old"
        }
        User("kamedon", 30, validation).validate().size.should.be(0)
        User("", 20, validation).validate().size.should.be(1)
        User("", 0, validation).validate().size.should.be(2)
    }

    @Test
    fun validate_2Test() {
        val validation = Validation<User> {
            of { name } be { length >= 5 } not "name: 5 characters or more"
            of { age } be { this >= 20 } not "age: Over 20 years old"
        }
        User("kamedon", 30, validation).validate().size.should.be(0)
        User("", 20, validation).validate().size.should.be(1)
        User("", 0, validation).validate().size.should.be(2)
    }

    @Test
    fun validate_3Test() {
        User2("kamedon", 30).validate().size.should.be(0)
        User2("", 20).validate().size.should.be(1)
        User2("", 0).validate().size.should.be(2)
    }
}


class User(val name: String, val age: Int, val validation: Validation<User>) {
    fun validate() = validation.validate(this)
}

class User2(val name: String, val age: Int) {

    val validation = Validation<User2> {
        of { name } be { length >= 5 } not "name: 5 characters or more"
        of { age } be { this >= 20 } not "age: Over 20 years old"
    }

    fun validate() = validation.validate(this)
}


