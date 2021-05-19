package com.example.fixturesmanage.ui.type

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.fixturesmanage.R
import com.example.fixturesmanage.databinding.FragmentTypeBinding

class TypeFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentTypeBinding>(inflater, R.layout.fragment_type, container, false)

        binding.createType.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_nav_type_to_typeCreateFragment)
        }
        setHasOptionsMenu(true)
        return binding.root
    }
}