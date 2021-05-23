package com.example.fixturesmanage.ui.status

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.fixturesmanage.R
import com.example.fixturesmanage.adapter.StatusAdapter
import com.example.fixturesmanage.dao.StatusDao
import com.example.fixturesmanage.database.FixturesManageDatabase
import com.example.fixturesmanage.databinding.FragmentStatusBinding

class StatusFragment : Fragment() {

    lateinit var mStatusDao: StatusDao

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        mStatusDao = FixturesManageDatabase.getInstance(this.requireContext()).statusDao()
        val binding = DataBindingUtil.inflate<FragmentStatusBinding>(inflater, R.layout.fragment_status, container, false)

        binding.createStatus.setOnClickListener { view : View ->
            view.findNavController().navigate(R.id.action_nav_status_to_statusCreateFragment)
        }
        setHasOptionsMenu(true)

        // Adapterに渡す配列を作成します
        val statuses = mStatusDao.getAll() //.map { u -> "${u.id}: ${u.name}" }
        // 一覧の作成
        val listView = binding.statusList
        val statusAdapter = StatusAdapter(requireContext(), R.layout.list_status, statuses)
        listView.adapter = statusAdapter

        // listViewの行がクリックされた時のイベントリスナー
        listView.setOnItemClickListener {parent, view, position, id ->
            // listViewのクリックされた行のテキストを取得
            val id = view.findViewById<TextView>(R.id.textViewId).text
            // 生成されたクラスに引数を渡して遷移
            val action = StatusFragmentDirections.actionNavStatusToStatusShowFragment(id.toString().toInt())
            view.findNavController().navigate(action)
        }

        return binding.root
    }
}