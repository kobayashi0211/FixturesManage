package com.example.fixturesmanage.ui.type

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
import com.example.fixturesmanage.dao.TypeDao
import com.example.fixturesmanage.database.FixturesManageDatabase
import com.example.fixturesmanage.databinding.ShowTypeFragmentBinding
import com.google.android.material.snackbar.Snackbar

class TypeShowFragment : Fragment() {

    lateinit var mTypeDao: TypeDao
    private val args: TypeShowFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mTypeDao = FixturesManageDatabase.getInstance(this.requireContext()).typeDao()
        val binding = DataBindingUtil.inflate<ShowTypeFragmentBinding>(inflater, R.layout.show_type_fragment, container, false)

        // 表示するモデルを取得
        val type = mTypeDao.findType(args.selectedId)

        binding.textViewName.text = type.name

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