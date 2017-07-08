package com.kamedon.validation

import com.taroid.knit.should
import org.junit.Test

/**
 * Created by Kamedon
 */
class User(val name: String, val age: Int, val validation: Validation<User>) {
    fun validate() = validation.validate(this)
}

class ValidationTest {

    @Test
    fun validate_1Test() {
        val validation = Validation<User> {
            "name"{
                be { name.length >= 5 } not "name: 5 characters or more"
            }
            "age"{
                be { age >= 20 } not "age: Over 20 years old"
            }
        }
        User("kamedon", 30, validation).validate().size.should.be(0)
        User("hoge", 20, validation).validate().size.should.be(1)
        User("", 0, validation).validate().size.should.be(2)
        // {name=[name: 5 characters or more], age=[age: Over 20 years old]}
    }
}
