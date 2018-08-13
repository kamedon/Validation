package com.kamedon.validation

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


class ValidationProperty<T> : ReadOnlyProperty<T?, Boolean> {

    override fun getValue(thisRef: T?, property: KProperty<*>): Boolean {
        return true
    }
}