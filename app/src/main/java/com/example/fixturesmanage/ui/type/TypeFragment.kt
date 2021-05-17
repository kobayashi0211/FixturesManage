package com.example.fixturesmanage.ui.type

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.room.Room
import com.example.fixturesmanage.R
import com.example.fixturesmanage.model.Type
import com.example.fixturesmanage.dao.TypeDao
import com.example.fixturesmanage.database.TypeDatabase
import com.example.fixturesmanage.databinding.FragmentTypeBinding

class TypeFragment : Fragment() {

    lateinit var mTypeDao: TypeDao

    private lateinit var typeViewModel: TypeViewModel

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
//        typeViewModel =
//                ViewModelProvider(this).get(TypeViewModel::class.java)
//        val root = inflater.inflate(R.layout.fragment_type, container, false)
//        val textView: TextView = root.findViewById(R.id.text_type)
//        typeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
//        return root
    }
}