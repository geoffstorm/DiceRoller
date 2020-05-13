package com.gstormdev.diceroller.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.gstormdev.diceroller.R
import com.gstormdev.diceroller.databinding.FragmentHistoryBinding
import com.gstormdev.diceroller.viewmodel.ViewModelFactory

class HistoryFragment : Fragment() {

    private val viewModelFactory by lazy { ViewModelFactory(this.requireContext().applicationContext) }
    private val historyViewModel by lazy { ViewModelProvider(this, viewModelFactory).get(HistoryViewModel::class.java) }
    private val historyAdapter = HistoryAdapter(this)

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHistoryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        binding.lifecycleOwner = this
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = historyAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        historyViewModel.rolls.observe(viewLifecycleOwner, Observer {
            historyAdapter.setData(it)
        })
        return binding.root
    }
}
