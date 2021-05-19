package com.example.fixturesmanage.ui.unit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.fixturesmanage.R
import com.example.fixturesmanage.databinding.FragmentUnitBinding

class UnitFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentUnitBinding>(inflater, R.layout.fragment_unit, container, false)

        binding.createUnit.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_nav_unit_to_unitCreateFragment)
        }
        setHasOptionsMenu(true)
        return binding.root
    }
}