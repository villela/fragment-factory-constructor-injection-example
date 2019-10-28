package com.matheusvillela.fragmentfactoryexample

import io.reactivex.Observable

interface ExampleViewModel {
    val identifier : Observable<String>
    val objectString : Observable<String>
}