package com.matheusvillela.fragmentfactoryexample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import toothpick.config.Module
import toothpick.ktp.KTP
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var scopeUuid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        scopeUuid = savedInstanceState?.getString("scope_uuid") ?: UUID.randomUUID().toString()
        val subScope = KTP.openRootScope()
            .openSubScope(scopeUuid)
        supportFragmentManager.fragmentFactory = ExampleFragmentFactory(subScope)
        val navigator = Navigator()
        subScope.installModules(object : Module() {
            init {
                bind(Context::class.java).toInstance(this@MainActivity)
                bind(navigator.javaClass).toInstance(navigator)
            }
        })

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            navigator.navigateNextFragment()
        }
    }

    inner class Navigator {
        fun navigateNextFragment() {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(
                R.id.activity_main_left_container,
                ExampleFragment::class.java, bundleOf("argument" to ExampleArgument("left 123"))
            )
            transaction.replace(
                R.id.activity_main_right_container,
                ExampleFragment::class.java, bundleOf("argument" to ExampleArgument("right 987"))
            )
            transaction.addToBackStack(null).commit()
        }

        fun popBackStack() {
            supportFragmentManager.popBackStack()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("scope_uuid", scopeUuid)
        super.onSaveInstanceState(outState)
    }
}
