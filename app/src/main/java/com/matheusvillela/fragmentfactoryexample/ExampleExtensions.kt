package com.matheusvillela.fragmentfactoryexample

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable?.addTo(compositeDisposable: CompositeDisposable) = this?.let { compositeDisposable.add(this) }
