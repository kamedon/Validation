package com.kamedon.validation

import kotlin.reflect.KClass

object Validations {
    val map: MutableMap<String, Validation<out Any?>> = HashMap()

    fun <T> key(clazz: Class<T>): String {
        return clazz.simpleName!!
    }

    inline fun <reified T> define(init: ValidationBuilder<T>.() -> Unit) {
        val builder = ValidationBuilder<T>()
        val validation = builder.apply(init).build()
        map[key(T::class.java)] = validation
    }


    operator fun invoke(init: Validations.() -> Unit) = apply(init)

    fun <T : Any> get(clazz: KClass<T>): Validation<T>? {
        val validation = map[key(clazz::class.java)]
                ?: throw IllegalArgumentException("not found validation")
        return validation as Validation<T>
    }

}
