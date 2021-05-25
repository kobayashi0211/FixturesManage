package com.example.fixturesmanage.ui.unit

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
import com.example.fixturesmanage.databinding.EditUnitFragmentBinding
import com.google.android.material.snackbar.Snackbar

class UnitEditFragment : Fragment() {

    companion object {
        fun newInstance() = UnitEditFragment()
    }

    lateinit var mUnitDao: UnitDao
    private val args: UnitShowFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mUnitDao = FixturesManageDatabase.getInstance(this.requireContext()).unitDao()
        val binding = DataBindingUtil.inflate<EditUnitFragmentBinding>(inflater, R.layout.edit_unit_fragment, container, false)

        // 表示するモデルを取得
        val unit = mUnitDao.findUnit(args.selectedId)
        binding.unitName = unit.name

        binding.editUnitButton.setOnClickListener { view : View ->
            val editText = binding.editTextName
            val name = editText.text.toString()

            /* ボタンクリックのタイミングでキーボードを閉じる */
            val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            if(name.isNotBlank()){
                if(mUnitDao.existName(name)){
                    Snackbar.make(view, "${name}は登録済みです", Snackbar.LENGTH_SHORT).show();
                }else{
                    val newUnit = mUnitDao.findUnit(args.selectedId)
                    newUnit.name = name
                    mUnitDao.update(newUnit)
                    Snackbar.make(view, "${name}を更新しました", Snackbar.LENGTH_SHORT).show();
                    val action = UnitEditFragmentDirections.actionUnitEditFragmentToUnitShowFragment(args.selectedId)
                    view.findNavController().navigate(action)
                }
            }else{
                Snackbar.make(view, "「単位名」を入力してください", Snackbar.LENGTH_SHORT).show();
            }
        }

        setHasOptionsMenu(true)
        return binding.root
//        return inflater.inflate(R.layout.create_unit_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(UnitShowViewModel::class.java)
        // TODO: Use the ViewModel
    }

}