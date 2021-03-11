package com.matheusvillela.githubdemoapp.shared

abstract class OnClickSubscriber<T> {
    private val subscribers = mutableListOf<Subscriber<T>>()

    fun onClick(obj: T) {
        subscribers.forEach { it.onClick(obj) }
    }

    fun subscribe(subscriber: Subscriber<T>) {
        subscribers.add(subscriber)
    }

    fun interface Subscriber<T> {
        fun onClick(obj: T)
    }
}