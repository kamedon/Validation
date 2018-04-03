# Kotlin Validation

[![Build Status](https://www.bitrise.io/app/52bf5677c2ea2255/status.svg?token=VefnKnVA0lwrTocWOGkTSg&branch=master)](https://www.bitrise.io/app/52bf5677c2ea2255)

## Usage

```
class User(val name: String, val age: Int)

class ValidationTest {

    val validation = Validation<User> {
        "name"{
            be { name.length >= 5 } not "name: 5 characters or more"
            be { name.length <= 10 } not "name: 10 characters or less"
        }
        "age"{
            be { age >= 20 } not "age: Over 20 years old"
        }
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
```

see: [ValidationTest.kt](https://github.com/kamedon/Validation/blob/master/validation/src/test/java/com/kamedon/validation/ValidationTest.kt)

## Installation

```
repositories {
    maven { url 'http://kamedon.github.com/Validation/repository' }
}

dependencies {
    implementation 'com.kamedon:kotlin-validation:0.4.0'
}
```

## License
This software is released under the MIT License, see LICENSE.txt.