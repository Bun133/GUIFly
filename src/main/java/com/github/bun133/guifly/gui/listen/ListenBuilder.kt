package com.github.bun133.guifly.gui.listen

import kotlin.reflect.KClass

// TODO implement
class ListenBuilder {
    internal fun build(): MutableList<Pair<KClass<*>, Listen<*>>> {
        return listeners
    }

    val listeners = mutableListOf<Pair<KClass<*>, Listen<*>>>()
    inline fun <reified T> addListen(listen: Listen<T>) {
        listeners.add(T::class to listen)
    }

    inline fun <reified T> event(crossinline f: (T) -> Unit) {
        addListen(object : Listen<T> {
            override fun on(t: T) {
                f(t)
            }
        })
    }
}