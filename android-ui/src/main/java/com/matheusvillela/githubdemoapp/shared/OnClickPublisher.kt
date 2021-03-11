package com.matheusvillela.githubdemoapp.shared

abstract class OnClickPublisher<T>(private val subscriber: OnClickSubscriber<T>) {
    fun onClick(obj: T) {
        subscriber.onClick(obj)
    }
}