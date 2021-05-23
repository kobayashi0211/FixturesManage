package com.example.fixturesmanage.ui.fixture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.fixturesmanage.R
import com.example.fixturesmanage.adapter.FixtureAdapter
import com.example.fixturesmanage.dao.FixtureDao
import com.example.fixturesmanage.database.FixturesManageDatabase
import com.example.fixturesmanage.databinding.FragmentFixtureBinding
import com.example.fixturesmanage.ui.type.TypeFragmentDirections

class FixtureFragment : Fragment() {

    lateinit var mFixtureDao: FixtureDao

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mFixtureDao = FixturesManageDatabase.getInstance(this.requireContext()).fixtureDao()
        val binding = DataBindingUtil.inflate<FragmentFixtureBinding>(inflater, R.layout.fragment_fixture, container, false)

        binding.createFixture.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_nav_fixture_to_fixtureCreateFragment)
        }
        setHasOptionsMenu(true)

        // Adapterに渡す配列を作成します
        val fixtures = mFixtureDao.getAll() //.map { u -> "${u.id}: ${u.name}" }
        // 一覧の作成
        val listView = binding.fixtureList
        val fixtureAdapter = FixtureAdapter(requireContext(), R.layout.list_fixture, fixtures)
        listView.adapter = fixtureAdapter

        // listViewの行がクリックされた時のイベントリスナー
        listView.setOnItemClickListener {parent, view, position, id ->
            // listViewのクリックされた行のテキストを取得
            val id = view.findViewById<TextView>(R.id.textViewId).text
            // 生成されたクラスに引数を渡して遷移
            val action = FixtureFragmentDirections.actionNavFixtureToFixtureShowFragment(id.toString().toInt())
            view.findNavController().navigate(action)
        }

        return binding.root
    }
}