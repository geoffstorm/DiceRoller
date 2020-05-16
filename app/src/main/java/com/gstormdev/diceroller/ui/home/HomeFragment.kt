package com.gstormdev.diceroller.ui.home

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.gstormdev.diceroller.R
import com.gstormdev.diceroller.databinding.FragmentHomeBinding
import com.gstormdev.diceroller.util.hideKeyboard
import com.gstormdev.diceroller.viewmodel.ViewModelFactory

class HomeFragment : Fragment() {

    private val viewModelFactory by lazy { ViewModelFactory(this.requireContext().applicationContext) }
    private val homeViewModel by lazy { ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java) }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.viewmodel = homeViewModel
        binding.lifecycleOwner = this
        binding.tvRollList.movementMethod = ScrollingMovementMethod()
        binding.btnRoll.setOnClickListener { v ->
            v.hideKeyboard()
            homeViewModel.rollDice()
        }

        return binding.root
    }
}
