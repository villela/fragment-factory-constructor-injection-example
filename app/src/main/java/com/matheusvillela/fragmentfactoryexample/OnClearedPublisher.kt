package com.matheusvillela.fragmentfactoryexample

import toothpick.InjectConstructor

@InjectConstructor
class OnClearedPublisher(private val subscriber: OnClearedSubscriber) {
    fun publish() {
        subscriber.onCleared()
    }
}