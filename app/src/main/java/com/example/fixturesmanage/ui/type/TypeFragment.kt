package com.example.fixturesmanage.ui.type

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.fixturesmanage.R
import com.example.fixturesmanage.adapter.FixtureAdapter
import com.example.fixturesmanage.adapter.TypeAdapter
import com.example.fixturesmanage.dao.TypeDao
import com.example.fixturesmanage.database.FixturesManageDatabase
import com.example.fixturesmanage.databinding.FragmentTypeBinding

class TypeFragment : Fragment() {

    lateinit var mTypeDao: TypeDao

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

        mTypeDao = FixturesManageDatabase.getInstance(this.requireContext()).typeDao()
        val binding = DataBindingUtil.inflate<FragmentTypeBinding>(inflater, R.layout.fragment_type, container, false)

        binding.createType.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_nav_type_to_typeCreateFragment)
        }
        setHasOptionsMenu(true)

        // Adapterに渡す配列を作成します
        var types = mTypeDao.getAll() //.map { u -> "${u.id}: ${u.name}" }
        // 一覧の作成
        val listView = binding.typeList
        var typeAdapter = TypeAdapter(requireContext(), R.layout.list_type, types)
        listView.adapter = typeAdapter

        // listViewの行がクリックされた時のイベントリスナー
        listView.setOnItemClickListener {parent, view, position, id ->
            // listViewのクリックされた行のテキストを取得
            val id = view.findViewById<TextView>(R.id.textViewId).text
            // 生成されたクラスに引数を渡して遷移
            val action = TypeFragmentDirections.actionNavTypeToTypeShowFragment(id.toString().toInt())
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
                types = mTypeDao.includeName("%${searchText}%")
                typeAdapter = TypeAdapter(requireContext(), R.layout.list_unit, types)
                listView.adapter = typeAdapter
            }
        })
        binding.buttonSearch.setOnClickListener { view : View ->
            var searchText = binding.editSearchName.text
            types = mTypeDao.includeName("%${searchText}%")
            typeAdapter = TypeAdapter(requireContext(), R.layout.list_unit, types)
            listView.adapter = typeAdapter

            /* ボタンクリックのタイミングでキーボードを閉じる */
            val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }

        return binding.root
    }
}