package com.kubernights.tp3.parcialnw.ui.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kubernights.tp3.parcialnw.R
import com.kubernights.tp3.parcialnw.ui.viewmodel.AdoptionViewModel

class AdoptionFragment : Fragment() {

    companion object {
        fun newInstance() = AdoptionFragment()
    }

    private lateinit var viewModel: AdoptionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_adoption, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AdoptionViewModel::class.java)
        // TODO: Use the ViewModel
    }

}