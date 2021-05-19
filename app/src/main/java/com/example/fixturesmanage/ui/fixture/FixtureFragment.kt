package com.example.fixturesmanage.ui.fixture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.fixturesmanage.R
import com.example.fixturesmanage.databinding.FragmentFixtureBinding

class FixtureFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentFixtureBinding>(inflater, R.layout.fragment_fixture, container, false)

        binding.createFixture.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_nav_fixture_to_fixtureCreateFragment)
        }
        setHasOptionsMenu(true)
        return binding.root
    }
}