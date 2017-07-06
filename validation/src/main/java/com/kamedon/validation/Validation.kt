package com.kamedon.validation

/**
 * Created by kamei.hidetoshi on 2017/07/06.
 */
class Validation<in T>(val validations: List<Pair<T.() -> Boolean, String>>) {

    companion object {
        operator fun <T> invoke(init: ValidationBuilder<T>.() -> Unit): Validation<T> {
            val builder = ValidationBuilder<T>()
            return builder.apply(init).build()
        }
    }

    fun validate(value: T) = validations.filter { !it.first.invoke(value) }.map { it.second }

}

class ValidationBuilder<T> {
    var validations: MutableList<Pair<T.() -> Boolean, String>> = mutableListOf()


    fun <R> of(field: T.() -> R) = field

    inline infix fun <R> (T.() -> R).be(crossinline validate: R.() -> Boolean): T.() -> Boolean {
        return { ->
            validate(this@be.invoke(this))
        }
    }

    fun be(validate: T.() -> Boolean) = validate

    infix fun (T.() -> Boolean).not(error: String) {
        validations.add(this to error)
    }

    fun build(): Validation<T> {
        return Validation(validations)
    }

}




