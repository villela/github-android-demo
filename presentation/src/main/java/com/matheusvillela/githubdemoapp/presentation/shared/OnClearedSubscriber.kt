package com.matheusvillela.githubdemoapp.presentation.shared

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

    fun interface Subscriber {
        fun onCleared()
    }
}