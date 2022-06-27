package com.midterm.english_app.view

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.midterm.english_app.R
import com.midterm.english_app.databinding.FragmentTensesBinding
import com.midterm.english_app.viewmodel.TensesRouterViewModel
import javax.inject.Inject

class TensesFragment : Fragment {

    @Inject
    constructor() {
        App.appComponent
    }

    @Inject
    lateinit var tensesRouterViewModel: TensesRouterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentTensesBinding>(inflater, R.layout.fragment_tenses, container, false)
        binding.tensesRouterViewModel = tensesRouterViewModel
        binding.executePendingBindings()
        val view = binding.root

        return view
    }
}
