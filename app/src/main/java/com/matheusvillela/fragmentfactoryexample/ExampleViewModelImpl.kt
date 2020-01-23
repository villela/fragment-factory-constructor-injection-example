package com.matheusvillela.fragmentfactoryexample

import io.reactivex.subjects.BehaviorSubject
import toothpick.InjectConstructor

@InjectConstructor
class ExampleViewModelImpl(
    argument: ExampleArgument,
    onClearedSubscriber: OnClearedSubscriber) : ExampleViewModel {
    override val identifier: BehaviorSubject<String> =
        BehaviorSubject.createDefault(argument.identifier)
    override val objectString: BehaviorSubject<String> =
        BehaviorSubject.createDefault(this.toString())

    init {
        onClearedSubscriber.subscribe(object  : OnClearedSubscriber.Subscriber {
            override fun onCleared() {
                // bla
            }
        })
    }
}