package com.example.fixturesmanage.ui.status

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fixturesmanage.R
import com.example.fixturesmanage.dao.StatusDao
import com.example.fixturesmanage.database.FixturesManageDatabase
import com.example.fixturesmanage.databinding.ShowStatusFragmentBinding
import com.example.fixturesmanage.ui.unit.UnitShowFragmentDirections
import com.google.android.material.snackbar.Snackbar

class StatusShowFragment : Fragment() {

    lateinit var mStatusDao: StatusDao
    private val args: StatusShowFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mStatusDao = FixturesManageDatabase.getInstance(this.requireContext()).statusDao()
        val binding = DataBindingUtil.inflate<ShowStatusFragmentBinding>(inflater, R.layout.show_status_fragment, container, false)

        // 表示するモデルを取得
        val status = mStatusDao.findStatus(args.selectedId)

        binding.textViewName.text = status.name

        // 編集がクリックされた時のイベントリスナー
        binding.buttonEdit.setOnClickListener { view : View ->
            // 表示中のモデルID
            val id = args.selectedId
            // 生成されたクラスに引数を渡して遷移
            val action = StatusShowFragmentDirections.actionStatusShowFragmentToStatusEditFragment(id.toString().toInt())
            view.findNavController().navigate(action)
        }

        binding.buttonDelete.setOnClickListener { view : View ->
            AlertDialog.Builder(this.requireContext()) // FragmentではActivityを取得して生成
                .setTitle("削除確認")
                .setMessage("${status.name} を削除しますか？")
                .setPositiveButton("OK") { dialog, which ->
                    mStatusDao.delete(status)
                    Snackbar.make(view, "${status.name} を削除しました", Snackbar.LENGTH_SHORT).show();
                    view.findNavController().navigate(R.id.action_statusShowFragment_to_nav_status)
                }
                .setNegativeButton("No") { dialog, which -> /* 何もしない */ }
                .show()
        }

        setHasOptionsMenu(true)
        return binding.root
    }

}