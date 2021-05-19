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
import com.example.fixturesmanage.R
import com.example.fixturesmanage.dao.UnitDao
import com.example.fixturesmanage.database.FixturesManageDatabase
import com.example.fixturesmanage.databinding.CreateUnitFragmentBinding
import com.example.fixturesmanage.model.Unit
import com.google.android.material.snackbar.Snackbar

class UnitCreateFragment : Fragment() {

    companion object {
        fun newInstance() = UnitCreateFragment()
    }

//    private lateinit var viewModel: UnitShowViewModel
    lateinit var mUnitDao: UnitDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mUnitDao = FixturesManageDatabase.getInstance(this.requireContext()).unitDao()
        val binding = DataBindingUtil.inflate<CreateUnitFragmentBinding>(inflater, R.layout.create_unit_fragment, container, false)

        binding.createUnitButton.setOnClickListener { view : View ->
            val editText = binding.editTextName
            val name = editText.text.toString()

            /* ボタンクリックのタイミングでキーボードを閉じる */
            val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            if(name.isNotBlank()){
                if(mUnitDao.existName(name)){
                    Snackbar.make(view, "${name}は登録済みです", Snackbar.LENGTH_SHORT).show();
                }else{
                    val newUnit = Unit(0, name)
                    mUnitDao.insert(newUnit)
                    Snackbar.make(view, "${name}を登録しました", Snackbar.LENGTH_SHORT).show();
                    view.findNavController().navigate(R.id.action_unitCreateFragment_to_nav_unit)
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