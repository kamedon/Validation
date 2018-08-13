package com.kamedon.validation

object Validations {
    val map: MutableMap<String, Validation<out Any?>> = HashMap()

    fun <T> key(clazz: Class<T>): String {
        return "${clazz.`package`.name}.${clazz.simpleName!!}"
    }

    inline fun <reified T> define(init: ValidationBuilder<T>.() -> Unit) {
        val builder = ValidationBuilder<T>()
        val validation = builder.apply(init).build()
        map[key(T::class.java)] = validation
    }


    operator fun invoke(init: Validations.() -> Unit) = apply(init)

    inline fun <reified T> get(): Validation<T> {
        val validation = map[key(T::class.java)]
                ?: throw IllegalArgumentException("not found validation")
        return validation as Validation<T>
    }

}

inline fun <reified T> T.vaild(): Map<String, List<String>> {
    val v = Validations.get<T>()
    return v.validate(this)
}
