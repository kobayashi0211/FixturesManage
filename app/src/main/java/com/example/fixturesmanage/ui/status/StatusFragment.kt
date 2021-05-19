package com.example.fixturesmanage.ui.status

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.fixturesmanage.R
import com.example.fixturesmanage.databinding.FragmentStatusBinding

class StatusFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentStatusBinding>(inflater, R.layout.fragment_status, container, false)

        binding.createStatus.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_nav_status_to_statusCreateFragment)
        }
        setHasOptionsMenu(true)
        return binding.root
    }
}