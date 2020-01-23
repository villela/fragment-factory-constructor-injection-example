package com.matheusvillela.fragmentfactoryexample

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.lifecycle.LifecycleObserver
import toothpick.Scope
import toothpick.config.Module
import java.util.*
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import toothpick.ktp.KTP
import java.io.Serializable


class ExampleFragmentFactory(private val scope: Scope) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return createFragment(classLoader, className, null, null)
    }

    override fun instantiateWithArguments(
        classLoader: ClassLoader, className: String,
        mWho: String, arguments: Bundle?
    ): Fragment {
        return createFragment(classLoader, className, mWho, arguments)
    }

    private fun createFragment(
        classLoader: ClassLoader, className: String,
        mWho: String?, arguments: Bundle?
    ): Fragment {
        val cls = loadFragmentClass(classLoader, className)
        val who = mWho ?: UUID.randomUUID().toString()
        val subScope = scope.openSubScope(who) {
            it.installModules(object : Module() {
                init {
                    arguments?.let {
                        val obj: Serializable? = it.getSerializable("argument")
                        if (obj != null) {
                            bind(obj.javaClass).toInstance(obj)
                        }
                    }
                }
            }, ViewModelModule())
        }
        val publisher = subScope.getInstance(OnClearedPublisher::class.java)
        val fragment = subScope.getInstance(cls)
        fragment.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestroy() {
                if (fragment.isRemoving) {
                    KTP.closeScope(subScope)
                    fragment.lifecycle.removeObserver(this)
                    publisher.publish()
                }
            }
        })
        return fragment
    }
}