package com.example.fixturesmanage.ui.fixture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.fixturesmanage.R
import com.example.fixturesmanage.model.Unit
import com.example.fixturesmanage.dao.UnitDao
import com.example.fixturesmanage.database.UnitDatabase

class FixtureFragment : Fragment() {

    private lateinit var fixtureViewModel: FixtureViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        fixtureViewModel =
                ViewModelProvider(this).get(FixtureViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_fixture, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        fixtureViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}