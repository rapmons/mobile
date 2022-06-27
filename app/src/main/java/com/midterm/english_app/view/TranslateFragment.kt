package com.midterm.english_app.view

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.midterm.english_app.R
import com.midterm.english_app.databinding.FragmentTranslateBinding
import com.midterm.english_app.viewmodel.TranslateViewModel
import javax.inject.Inject

class TranslateFragment : Fragment {

    @Inject
    constructor() {
        App.appComponent
    }

    @Inject
    lateinit var translateViewModel: TranslateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentTranslateBinding>(
            inflater,
            R.layout.fragment_translate,
            container,
            false
        )
        binding.translateViewModel = translateViewModel
        binding.executePendingBindings()
        val view = binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}
