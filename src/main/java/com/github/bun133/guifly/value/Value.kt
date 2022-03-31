package com.github.bun133.guifly.value

open class Value<T>(defaultValue: T) {
    var value: T = defaultValue
        set(value) {
            field = value
            onChange(value)
        }

    private val listeners = mutableListOf<Value<T>.(T) -> Unit>()

    private fun onChange(value: T) {
        listeners.forEach { this.it(value) }
    }

    fun listen(listener: Value<T>.(T) -> Unit): Value<T> {
        listeners.add(listener)
        return this
    }

    fun unListen(listener: Value<T>.(T) -> Unit): Value<T> {
        listeners.remove(listener)
        return this
    }
}