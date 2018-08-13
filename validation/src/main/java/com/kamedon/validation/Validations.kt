package com.kamedon.validation

object Validations {
    private val map: MutableMap<String, Validation<*>> = HashMap()

    operator fun invoke(vararg validations: Validation<*>) {
        validations.forEach {
            map[it::class.java.name] = it
        }
    }

}
