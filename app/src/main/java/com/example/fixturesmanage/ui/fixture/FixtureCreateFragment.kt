package com.example.fixturesmanage.ui.fixture

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
import com.example.fixturesmanage.dao.FixtureDao
import com.example.fixturesmanage.database.FixturesManageDatabase
import com.example.fixturesmanage.databinding.CreateFixtureFragmentBinding
import com.example.fixturesmanage.model.Fixture
import com.google.android.material.snackbar.Snackbar

class FixtureCreateFragment : Fragment() {

    companion object {
        fun newInstance() = FixtureCreateFragment()
    }

//    private lateinit var viewModel: CreateFixtureViewModel
    lateinit var mFixtureDao: FixtureDao
    var validMsg: String=""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mFixtureDao = FixturesManageDatabase.getInstance(this.requireContext()).fixtureDao()
        val binding = DataBindingUtil.inflate<CreateFixtureFragmentBinding>(inflater, R.layout.create_fixture_fragment, container, false)

        binding.createFixtureButton.setOnClickListener { view : View ->
            // 名前
            val editText = binding.editTextName
            val name = editText.text.toString()
            // 種別
            val editType = binding.editSpinnerType
            val type = 1//editType.selectedItemPosition
            // 状態
            val editStatus = binding.editSpinnerStatus
            val status = 1//editStatus.selectedItemPosition
            // 個数
            val editQuantity = binding.editTextQuantity
            val quantity = Integer.parseInt(editQuantity.text.toString())
            // 単位
            val editUnit = binding.editSpinnerUnit
            val unit = 1//editUnit.selectedItemPosition

            /* ボタンクリックのタイミングでキーボードを閉じる */
            val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            if(validForm(name, type, status, quantity, unit)){

                if(mFixtureDao.existName(name)){
                    Snackbar.make(view, "${name}は登録済みです", Snackbar.LENGTH_SHORT).show();
                }else{
                    val newFixture = Fixture(0, name, type, status, quantity, unit)
                    mFixtureDao.insert(newFixture)
                    Snackbar.make(view, "${name}を登録しました", Snackbar.LENGTH_SHORT).show();
                    view.findNavController().navigate(R.id.action_fixtureCreateFragment_to_nav_fixture)
                }
            }else{
                Snackbar.make(view, validMsg, Snackbar.LENGTH_SHORT).show();
            }
        }

        setHasOptionsMenu(true)
        return binding.root
//        return inflater.inflate(R.layout.create_status_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(CreateStatusViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun validForm(name: String, type: Int, status: Int, quantity: Int, unit: Int): Boolean {
        if(name.isBlank()){
            validMsg = "「備品名」を入力してください"
            return false
        }else if(type==0){
            validMsg = "「種別」を入力してください"
            return false
        }else if(status==0){
            validMsg = "「状態」を入力してください"
            return false
        }else if(quantity==0){
            validMsg = "「個数」を入力してください"
            return false
        }else if(unit==0){
            validMsg = "「単位」を入力してください"
            return false
        }
        return true
    }

}