package com.matheusvillela.fragmentfactoryexample

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_example.*
import toothpick.InjectConstructor

@InjectConstructor
class ExampleFragment(private val viewModel: ExampleViewModel) : Fragment() {

    private var disposables = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_example, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.apply {
            identifier
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    fragment_example_text.text = it
                }.addTo(disposables)
            objectString
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    fragment_example2_text.text = it
                }.addTo(disposables)
        }
        fragment_example_text.setOnClickListener { startActivity(Intent(context, MainActivity::class.java)) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.dispose()
        disposables = CompositeDisposable()
    }
}