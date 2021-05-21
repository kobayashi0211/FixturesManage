package com.example.fixturesmanage.ui.unit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.fixturesmanage.R
import com.example.fixturesmanage.adapter.UnitAdapter
import com.example.fixturesmanage.dao.UnitDao
import com.example.fixturesmanage.database.FixturesManageDatabase
import com.example.fixturesmanage.databinding.FragmentUnitBinding

class UnitFragment : Fragment() {

    lateinit var mUnitDao: UnitDao

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mUnitDao = FixturesManageDatabase.getInstance(this.requireContext()).unitDao()
        val binding = DataBindingUtil.inflate<FragmentUnitBinding>(inflater, R.layout.fragment_unit, container, false)

        binding.createUnit.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_nav_unit_to_unitCreateFragment)
        }
        setHasOptionsMenu(true)

        // Adapterに渡す配列を作成します
        val units = mUnitDao.getAll() //.map { u -> "${u.id}: ${u.name}" }
        // 一覧の作成
        val listView = binding.unitList
        val unitAdapter = UnitAdapter(requireContext(), R.layout.list_unit, units)
        listView.adapter = unitAdapter

        // listViewの行がクリックされた時のイベントリスナー
        listView.setOnItemClickListener {parent, view, position, id ->
            // listViewのクリックされた行のテキストを取得
            val id = view.findViewById<TextView>(R.id.textViewId).text
            val name = view.findViewById<TextView>(R.id.textViewName).text
            // トーストで表示する
            Toast.makeText(requireContext(), "${id}: $name", Toast.LENGTH_LONG).show()

            // 生成されたクラスに引数を渡して遷移
            val action = UnitFragmentDirections.actionNavUnitToUnitShowFragment(id.toString().toInt())
            view.findNavController().navigate(action)
        }

        return binding.root
    }
}