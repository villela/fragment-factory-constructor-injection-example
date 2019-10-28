package com.matheusvillela.fragmentfactoryexample

import io.reactivex.subjects.BehaviorSubject
import toothpick.InjectConstructor

@InjectConstructor
class ExampleViewModelImpl(argument: ExampleArgument) : ExampleViewModel {
    override val identifier: BehaviorSubject<String> =
        BehaviorSubject.createDefault(argument.identifier)
    override val objectString: BehaviorSubject<String> =
        BehaviorSubject.createDefault(this.toString())
}