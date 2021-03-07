package com.matheusvillela.githubdemoapp.presentation.shared

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

internal fun Disposable?.addTo(compositeDisposable: CompositeDisposable) = this?.let { compositeDisposable.add(this) }