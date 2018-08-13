package com.kamedon.validation

import org.junit.Assert
import org.junit.Test

class ValidationsTest {
    class User(val name: String, val age: Int) {
        val valid by ValidationProperty()
    }

    class Account(val name: String) {
        val valid by ValidationProperty()
    }

    @Test
    fun syntaxText() {
        Validations {
            define<User> {
                "name"{
                    be { name.length >= 5 } not "name: 5 characters or more"
                    be { name.length <= 10 } not "name: 10 characters or less"
                }
                "age"{
                    be { age >= 20 } not "age: Over 20 years old"
                }
            }
        }
        Assert.assertTrue(true)
    }

    @Test
    fun validDataText() {
        Validations {
            define<User> {
                "name"{
                    be { name.length >= 5 } not "name: 5 characters or more"
                    be { name.length <= 10 } not "name: 10 characters or less"
                }
                "age"{
                    be { age >= 20 } not "age: Over 20 years old"
                }
            }
        }

        val user = User("kamedon", 30)
        val validation = Validations.get<User>()
        Assert.assertEquals(true, validation != null)
        val valid = validation?.validate(user) ?: return Assert.fail("validation not found")
        Assert.assertEquals(0, valid.size)
    }

    @Test
    fun invalidDataText() {
        Validations {
            define<User> {
                "name"{
                    be { name.length >= 5 } not "name: 5 characters or more"
                    be { name.length <= 10 } not "name: 10 characters or less"
                }
                "age"{
                    be { age >= 20 } not "age: Over 20 years old"
                }
            }
        }

        val user = User("kamedon", 0)
        val validation = Validations.get<User>()
        Assert.assertEquals(true, validation != null)
        val valid = validation?.validate(user) ?: return Assert.fail("validation not found")
        Assert.assertEquals(1, valid.size)
    }

    @Test
    fun multiTest() {
        Validations {
            define<User> {
                "name"{
                    be { name.length >= 5 } not "name: 5 characters or more"
                    be { name.length <= 10 } not "name: 10 characters or less"
                }
                "age"{
                    be { age >= 20 } not "age: Over 20 years old"
                }
            }
            define<Account> {
                "name"{
                    be { name.isNotBlank() } not "name is not blank"
                }

            }
        }

        val user = User("kamedon", 0)
        val validation = Validations.get<User>()
        val valid = validation?.validate(user) ?: return Assert.fail("validation not found")
        Assert.assertEquals(1, valid.size)

        val account = Account("")
        val validationAccount = Validations.get<Account>()
        val validAccount = validationAccount?.validate(account)
                ?: return Assert.fail("validation not found")
        Assert.assertEquals(1, validAccount.size)
    }


}