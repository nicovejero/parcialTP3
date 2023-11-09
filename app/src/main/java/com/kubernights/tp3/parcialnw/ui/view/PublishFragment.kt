package com.kubernights.tp3.parcialnw.ui.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kubernights.tp3.parcialnw.R
import com.kubernights.tp3.parcialnw.ui.viewmodel.PublishViewModel

class PublishFragment : Fragment() {

    companion object {
        fun newInstance() = PublishFragment()
    }

    private lateinit var viewModel: PublishViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_publish, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PublishViewModel::class.java)
        // TODO: Use the ViewModel
    }

}