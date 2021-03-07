package com.matheusvillela.githubdemoapp.presentation.shared

import toothpick.InjectConstructor

@InjectConstructor
class OnClearedPublisher(private val subscriber: OnClearedSubscriber) {
    fun publish() {
        subscriber.onCleared()
    }
}