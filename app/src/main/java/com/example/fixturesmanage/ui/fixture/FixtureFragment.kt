package com.example.fixturesmanage.ui.fixture

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.fixturesmanage.R
import com.example.fixturesmanage.adapter.FixtureAdapter
import com.example.fixturesmanage.dao.FixtureDao
import com.example.fixturesmanage.database.FixturesManageDatabase
import com.example.fixturesmanage.databinding.FragmentFixtureBinding

class FixtureFragment : Fragment() {

    lateinit var mFixtureDao: FixtureDao

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        /* 開かれたタイミングでキーボードを閉じる */
        val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (container != null) {
            inputManager.hideSoftInputFromWindow(container.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }

        mFixtureDao = FixturesManageDatabase.getInstance(this.requireContext()).fixtureDao()
        val binding = DataBindingUtil.inflate<FragmentFixtureBinding>(inflater, R.layout.fragment_fixture, container, false)

        binding.createFixture.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_nav_fixture_to_fixtureCreateFragment)
        }
        setHasOptionsMenu(true)

        // Adapterに渡す配列を作成します
        var fixtures = mFixtureDao.getAll() //.map { u -> "${u.id}: ${u.name}" }
        // 一覧の作成
        val listView = binding.fixtureList
        var fixtureAdapter = FixtureAdapter(requireContext(), R.layout.list_fixture, fixtures)
        listView.adapter = fixtureAdapter

        // listViewの行がクリックされた時のイベントリスナー
        listView.setOnItemClickListener {parent, view, position, id ->
            // listViewのクリックされた行のテキストを取得
            val id = view.findViewById<TextView>(R.id.textViewId).text
            // 生成されたクラスに引数を渡して遷移
            val action = FixtureFragmentDirections.actionNavFixtureToFixtureShowFragment(id.toString().toInt())
            view.findNavController().navigate(action)
        }

        binding.editSearchName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                listView.visibility = View.VISIBLE
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                listView.visibility = View.INVISIBLE
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var searchText = binding.editSearchName.text
                fixtures = mFixtureDao.includeName("%${searchText}%")
                fixtureAdapter = FixtureAdapter(requireContext(), R.layout.list_unit, fixtures)
                listView.adapter = fixtureAdapter
            }
        })
        binding.buttonSearch.setOnClickListener { view : View ->
            var searchText = binding.editSearchName.text
            fixtures = mFixtureDao.includeName("%${searchText}%")
            fixtureAdapter = FixtureAdapter(requireContext(), R.layout.list_unit, fixtures)
            listView.adapter = fixtureAdapter

            /* ボタンクリックのタイミングでキーボードを閉じる */
            val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }

        return binding.root
    }
}