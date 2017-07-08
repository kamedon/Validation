package com.kamedon.validation

/**
 * Created by Kamedon
 */
class Validation<T>(val validations: Map<String, ChildValidation<T>>) {

    companion object {
        operator fun <T> invoke(init: ValidationBuilder<T>.() -> Unit): Validation<T> {
            val builder = ValidationBuilder<T>()
            return builder.apply(init).build()
        }
    }

    fun validate(value: T): Map<String, List<String>> {
        val messages = mutableMapOf<String, List<String>>()
        validations.forEach { map ->
            val errors = map.value.validations.filter { !it.first.invoke(value) }.map { it.second }.takeIf { it.isNotEmpty() }
            errors?.also {
                messages.put(map.key, it)
            }
        }
        return messages
    }

}

class ValidationBuilder<T> {
    var childValidations: MutableMap<String, ChildValidation<T>> = mutableMapOf()

    operator fun String.invoke(init: ChildValidation<T>.() -> Unit) {
        childValidations.put(this, ChildValidation<T>().apply(init))
    }

    fun build(): Validation<T> {
        return Validation(childValidations)
    }

}

class ChildValidation<T> {
    var validations: MutableList<Pair<T.() -> Boolean, String>> = mutableListOf()

    fun be(validate: T.() -> Boolean) = validate

    infix fun (T.() -> Boolean).not(error: String) {
        validations.add(this to error)
    }

}

