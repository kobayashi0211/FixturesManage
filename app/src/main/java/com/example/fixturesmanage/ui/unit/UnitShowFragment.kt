package com.example.fixturesmanage.ui.unit

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fixturesmanage.R
import com.example.fixturesmanage.dao.UnitDao
import com.example.fixturesmanage.database.FixturesManageDatabase
import com.example.fixturesmanage.databinding.ShowUnitFragmentBinding
import com.google.android.material.snackbar.Snackbar

class UnitShowFragment : Fragment() {

    lateinit var mUnitDao: UnitDao
    private val args: UnitShowFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /* 開かれたタイミングでキーボードを閉じる */
        val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (container != null) {
            inputManager.hideSoftInputFromWindow(container.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }

        mUnitDao = FixturesManageDatabase.getInstance(this.requireContext()).unitDao()
        val binding = DataBindingUtil.inflate<ShowUnitFragmentBinding>(inflater, R.layout.show_unit_fragment, container, false)

        // 表示するモデルを取得
        val unit = mUnitDao.findUnit(args.selectedId)

        binding.textViewName.text = unit.name

        // 編集がクリックされた時のイベントリスナー
        binding.buttonEdit.setOnClickListener { view : View ->
            // 表示中のモデルID
            val id = args.selectedId
            // 生成されたクラスに引数を渡して遷移
            val action = UnitShowFragmentDirections.actionUnitShowFragmentToUnitEditFragment(id.toString().toInt())
            view.findNavController().navigate(action)
        }

        binding.buttonDelete.setOnClickListener { view : View ->
            AlertDialog.Builder(this.requireContext()) // FragmentではActivityを取得して生成
                .setTitle("削除確認")
                .setMessage("${unit.name} を削除しますか？")
                .setPositiveButton("OK") { dialog, which ->
                    mUnitDao.delete(unit)
                    Snackbar.make(view, "${unit.name} を削除しました", Snackbar.LENGTH_SHORT).show();
                    view.findNavController().navigate(R.id.action_unitShowFragment_to_nav_unit)
                }
                .setNegativeButton("No") { dialog, which -> /* 何もしない */ }
                .show()
        }


        setHasOptionsMenu(true)
        return binding.root
    }

}