package com.example.fixturesmanage.ui.type

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
import com.example.fixturesmanage.dao.TypeDao
import com.example.fixturesmanage.database.FixturesManageDatabase
import com.example.fixturesmanage.databinding.ShowTypeFragmentBinding
import com.example.fixturesmanage.ui.unit.UnitShowFragmentDirections
import com.google.android.material.snackbar.Snackbar

class TypeShowFragment : Fragment() {

    lateinit var mTypeDao: TypeDao
    private val args: TypeShowFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /* 開かれたタイミングでキーボードを閉じる */
        val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (container != null) {
            inputManager.hideSoftInputFromWindow(container.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }

        mTypeDao = FixturesManageDatabase.getInstance(this.requireContext()).typeDao()
        val binding = DataBindingUtil.inflate<ShowTypeFragmentBinding>(inflater, R.layout.show_type_fragment, container, false)

        // 表示するモデルを取得
        val type = mTypeDao.findType(args.selectedId)

        binding.textViewName.text = type.name

        // 編集がクリックされた時のイベントリスナー
        binding.buttonEdit.setOnClickListener { view : View ->
            // 表示中のモデルID
            val id = args.selectedId
            // 生成されたクラスに引数を渡して遷移
            val action = TypeShowFragmentDirections.actionTypeShowFragmentToTypeEditFragment(id.toString().toInt())
            view.findNavController().navigate(action)
        }

        binding.buttonDelete.setOnClickListener { view : View ->
            AlertDialog.Builder(this.requireContext()) // FragmentではActivityを取得して生成
                .setTitle("削除確認")
                .setMessage("${type.name} を削除しますか？")
                .setPositiveButton("OK") { dialog, which ->
                    mTypeDao.delete(type)
                    Snackbar.make(view, "${type.name} を削除しました", Snackbar.LENGTH_SHORT).show();
                    view.findNavController().navigate(R.id.action_typeShowFragment_to_nav_type)
                }
                .setNegativeButton("No") { dialog, which -> /* 何もしない */ }
                .show()
        }

        setHasOptionsMenu(true)
        return binding.root
    }

}