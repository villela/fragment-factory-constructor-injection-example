package com.matheusvillela.fragmentfactoryexample

import toothpick.config.Module

class ViewModelModule : Module() {

    init {
        bind(ExampleViewModel::class.java).to(ExampleViewModelImpl::class.java).singleton()
    }
}