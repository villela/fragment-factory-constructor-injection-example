package com.matheusvillela.fragmentfactoryexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import toothpick.ktp.KTP
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var scopeUuid: String

    override fun onCreate(savedInstanceState: Bundle?) {
        scopeUuid = savedInstanceState?.getString("scope_uuid") ?: UUID.randomUUID().toString()
        val subScope = KTP.openRootScope()
            .openSubScope(scopeUuid)
        supportFragmentManager.fragmentFactory = ExampleFragmentFactory(subScope)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(
                R.id.activity_main_left_container,
                ExampleFragment::class.java, bundleOf("argument" to ExampleArgument("left 123"))
            )
            transaction.replace(
                R.id.activity_main_right_container,
                ExampleFragment::class.java, bundleOf("argument" to ExampleArgument("right 987"))
            )
            transaction.commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("scope_uuid", scopeUuid)
        super.onSaveInstanceState(outState)
    }
}
