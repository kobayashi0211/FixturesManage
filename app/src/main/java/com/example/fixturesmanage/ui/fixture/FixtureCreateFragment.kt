package com.example.fixturesmanage.ui.fixture

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.fixturesmanage.R
import com.example.fixturesmanage.adapter.StatusAdapter
import com.example.fixturesmanage.adapter.TypeAdapter
import com.example.fixturesmanage.adapter.UnitAdapter
import com.example.fixturesmanage.dao.FixtureDao
import com.example.fixturesmanage.dao.StatusDao
import com.example.fixturesmanage.dao.TypeDao
import com.example.fixturesmanage.dao.UnitDao
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
    lateinit var mTypeDao: TypeDao
    lateinit var mStatusDao: StatusDao
    lateinit var mUnitDao: UnitDao
    var validMsg: String=""

    var selectedType: Int = 0
    var selectedStatus: Int = 0
    var selectedUnit: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mFixtureDao = FixturesManageDatabase.getInstance(this.requireContext()).fixtureDao()
        mTypeDao = FixturesManageDatabase.getInstance(this.requireContext()).typeDao()
        mStatusDao = FixturesManageDatabase.getInstance(this.requireContext()).statusDao()
        mUnitDao = FixturesManageDatabase.getInstance(this.requireContext()).unitDao()
        val binding = DataBindingUtil.inflate<CreateFixtureFragmentBinding>(inflater, R.layout.create_fixture_fragment, container, false)

        // ???????????????????????????
        val typeAdapter = TypeAdapter(requireContext(), R.layout.list_spinner, mTypeDao.getAll())
        typeAdapter.setDropDownViewResource(R.layout.list_spinner)
        val statusAdapter = StatusAdapter(requireContext(), R.layout.list_spinner, mStatusDao.getAll())
        statusAdapter.setDropDownViewResource(R.layout.list_spinner)
        val unitAdapter = UnitAdapter(requireContext(), R.layout.list_spinner, mUnitDao.getAll())
        unitAdapter.setDropDownViewResource(R.layout.list_spinner)
        // ??????
        val spinnerType = binding.editSpinnerType
        spinnerType.adapter = typeAdapter
        spinnerType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //????????????????????????????????????
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val spinnerParent = parent as Spinner
                val item = spinnerParent.selectedItem as com.example.fixturesmanage.model.Type
                selectedType = item.id
            }
            //??????????????????????????????????????????
            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }
        // ??????
        val spinnerStatus = binding.editSpinnerStatus
        spinnerStatus.adapter = statusAdapter
        spinnerStatus.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //????????????????????????????????????
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val spinnerParent = parent as Spinner
                val item = spinnerParent.selectedItem as com.example.fixturesmanage.model.Status
                selectedStatus = item.id
            }
            //??????????????????????????????????????????
            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }
        // ??????
        val spinnerUnit = binding.editSpinnerUnit
        spinnerUnit.adapter = unitAdapter
        spinnerUnit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            //????????????????????????????????????
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val spinnerParent = parent as Spinner
                val item = spinnerParent.selectedItem as com.example.fixturesmanage.model.Unit
                selectedUnit = item.id
            }
            //??????????????????????????????????????????
            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }

        binding.createFixtureButton.setOnClickListener { view : View ->
            // ??????
            val editText = binding.editTextName
            val name = editText.text.toString()
            // ??????
            val type = selectedType
            // ??????
            val status = selectedStatus
            // ??????
            val editQuantity = binding.editTextQuantity
            val quantity = if(editQuantity.text.isBlank()) {
                -1
            } else {
                editQuantity.text.toString().toInt()
            }

            // ??????
            val unit = selectedUnit

            /* ????????????????????????????????????????????????????????????????????? */
            val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            if(validForm(name, type, status, quantity, unit)){

                if(mFixtureDao.existName(name)){
                    Snackbar.make(view, "${name}?????????????????????", Snackbar.LENGTH_SHORT).show();
                }else{
                    val newFixture = Fixture(0, name, type, status, quantity, unit)
                    mFixtureDao.insert(newFixture)
                    Snackbar.make(view, "${name}?????????????????????", Snackbar.LENGTH_SHORT).show();
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
            validMsg = "??????????????????????????????????????????"
            return false
        }else if(type==0){
            validMsg = "???????????????????????????????????????"
            return false
        }else if(status==0){
            validMsg = "???????????????????????????????????????"
            return false
        }else if(quantity < 0){
            validMsg = "???????????????????????????????????????"
            return false
        }else if(unit==0){
            validMsg = "???????????????????????????????????????"
            return false
        }
        return true
    }

}