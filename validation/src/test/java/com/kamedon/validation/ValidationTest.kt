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
                be { age >= 30 } not "age: Over 20 years old"
            }
        }
        print(User("kamedon", 30, validation).validate())
        print(User("", 0, validation).validate())
        val errors = User("kamedon", 30, validation)
//        User("", 20, validation).validate().size.should.be(1)
//        User("", 0, validation).validate().size.should.be(2)
    }
}
