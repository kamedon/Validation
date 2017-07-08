# Kotlin Validation

## Usage

```
class User(val name: String, val age: Int)

val validation = Validation<User> {
    "name"{
        be { name.length >= 5 } not "name: 5 characters or more"
        be { name.length <= 16 } not "name: 16 characters or less"
    }
    "age"{
        be { age >= 20 } not "age: Over 20 years old"
    }
}

User("kamedon", 30, validation).validate().size.should.be(0)
User("hoge", 20, validation).validate().size.should.be(1)
User("", 0, validation).validate().size.should.be(2)
// {name=[name: 5 characters or more], age=[age: Over 20 years old]}
```

see: [ValidationTest.kt](https://github.com/kamedon/Validation/blob/master/validation/src/test/java/com/kamedon/validation/ValidationTest.kt)

## Installation

```
repositories {
    maven { url 'http://kamedon.github.com/Validation/repository' }
}

dependencies {
    implementation 'com.kamedon:kotlin-validation:0.1.0'
}
```

## License
This software is released under the MIT License, see LICENSE.txt.