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
            val errors = map.value.validations.asSequence().filter { !it.validate.invoke(value) }.map {
                it.callback?.invoke()
                it.message
            }.toList().takeIf { it.isNotEmpty() }
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
    var validations: MutableList<Validable<T>> = mutableListOf()

    fun be(validate: T.() -> Boolean) = validate

    infix fun (T.() -> Boolean).not(error: String) {
        validations.add(Validable(this, error))
    }

    infix fun (T.() -> Boolean).not(syntax: with) = CallbackWrapper(this)
    infix fun (T.() -> Boolean).not(syntax: error) = MessageWrapper(this)

    infix fun MessageWrapper<T>.message(message: String) {
        validations.add(Validable(f, message))
    }

    inline infix fun MessageWrapper<T>.message(message: () -> String) {
        validations.add(Validable(f, message()))
    }

    operator fun String.invoke(f: () -> Unit): Pair<String, () -> Unit> {
        return Pair(this, f)
    }

    infix fun CallbackWrapper<T>.callback(message: Pair<String, () -> Unit>) {
        validations.add(Validable(f, message.first, message.second))
    }

}

class Validable<T>(val validate: T.() -> Boolean, val message: String, val callback: (() -> Unit)? = null)

object error
class MessageWrapper<T>(val f: T.() -> Boolean)


object with
class CallbackWrapper<T>(val f: T.() -> Boolean)

