package com.matheusvillela.fragmentfactoryexample

import toothpick.InjectConstructor

@InjectConstructor
class OnClearedSubscriber {
    private val subscribers = mutableListOf<Subscriber>()

    fun onCleared() {
        subscribers.forEach { it.onCleared() }
    }

    fun subscribe(subscriber: Subscriber) {
        subscribers.add(subscriber)
    }

    interface Subscriber {
        fun onCleared()
    }


}