package com.example.fixturesmanage.ui.type

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.fixturesmanage.R
import com.example.fixturesmanage.adapter.TypeAdapter
import com.example.fixturesmanage.adapter.UnitAdapter
import com.example.fixturesmanage.dao.TypeDao
import com.example.fixturesmanage.database.FixturesManageDatabase
import com.example.fixturesmanage.databinding.FragmentTypeBinding
import com.example.fixturesmanage.ui.unit.UnitFragmentDirections

class TypeFragment : Fragment() {

    lateinit var mTypeDao: TypeDao

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mTypeDao = FixturesManageDatabase.getInstance(this.requireContext()).typeDao()
        val binding = DataBindingUtil.inflate<FragmentTypeBinding>(inflater, R.layout.fragment_type, container, false)

        binding.createType.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_nav_type_to_typeCreateFragment)
        }
        setHasOptionsMenu(true)

        // Adapterに渡す配列を作成します
        val units = mTypeDao.getAll() //.map { u -> "${u.id}: ${u.name}" }
        // 一覧の作成
        val listView = binding.typeList
        val typeAdapter = TypeAdapter(requireContext(), R.layout.list_type, units)
        listView.adapter = typeAdapter

        // listViewの行がクリックされた時のイベントリスナー
        listView.setOnItemClickListener {parent, view, position, id ->
            // listViewのクリックされた行のテキストを取得
            val id = view.findViewById<TextView>(R.id.textViewId).text
            // 生成されたクラスに引数を渡して遷移
            val action = TypeFragmentDirections.actionNavTypeToTypeShowFragment(id.toString().toInt())
            view.findNavController().navigate(action)
        }

        return binding.root
    }
}