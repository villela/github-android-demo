package com.matheusvillela.githubdemoapp.shared

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

fun Disposable?.addTo(compositeDisposable: CompositeDisposable) = this?.let { compositeDisposable.add(this) }