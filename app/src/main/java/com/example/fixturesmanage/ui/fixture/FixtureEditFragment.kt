package com.example.fixturesmanage.ui.fixture

import android.content.Context
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fixturesmanage.R
import com.example.fixturesmanage.adapter.StatusAdapter
import com.example.fixturesmanage.adapter.TypeAdapter
import com.example.fixturesmanage.adapter.UnitAdapter
import com.example.fixturesmanage.dao.FixtureDao
import com.example.fixturesmanage.dao.StatusDao
import com.example.fixturesmanage.dao.TypeDao
import com.example.fixturesmanage.dao.UnitDao
import com.example.fixturesmanage.database.FixturesManageDatabase
import com.example.fixturesmanage.databinding.EditFixtureFragmentBinding
import com.google.android.material.snackbar.Snackbar

class FixtureEditFragment : Fragment() {

    companion object {
        fun newInstance() = FixtureEditFragment()
    }

    lateinit var mFixtureDao: FixtureDao
    lateinit var mTypeDao: TypeDao
    lateinit var mStatusDao: StatusDao
    lateinit var mUnitDao: UnitDao
    var validMsg: String=""

    var selectedType: Int = 0
    var selectedStatus: Int = 0
    var selectedUnit: Int = 0

    private val args: FixtureShowFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mFixtureDao = FixturesManageDatabase.getInstance(this.requireContext()).fixtureDao()
        mTypeDao = FixturesManageDatabase.getInstance(this.requireContext()).typeDao()
        mStatusDao = FixturesManageDatabase.getInstance(this.requireContext()).statusDao()
        mUnitDao = FixturesManageDatabase.getInstance(this.requireContext()).unitDao()
        val binding = DataBindingUtil.inflate<EditFixtureFragmentBinding>(inflater, R.layout.edit_fixture_fragment, container, false)

        // ??????????????????????????????
        val fixture = mFixtureDao.findFixture(args.selectedId)
        binding.fixtureName = fixture.name
        binding.fixtureType = fixture.type
        binding.fixtureStatus = fixture.status
        binding.fixtureQuantity = fixture.quantity.toString()
        binding.fixtureUnit = fixture.unit

        // ???????????????????????????
        val typeAdapter = TypeAdapter(requireContext(), R.layout.list_spinner, mTypeDao.getAll())
        typeAdapter.setDropDownViewResource(R.layout.list_spinner)
        val statusAdapter = StatusAdapter(requireContext(), R.layout.list_spinner, mStatusDao.getAll())
        statusAdapter.setDropDownViewResource(R.layout.list_spinner)
        val unitAdapter = UnitAdapter(requireContext(), R.layout.list_spinner, mUnitDao.getAll())
        unitAdapter.setDropDownViewResource(R.layout.list_spinner)

        val typeIdx = mTypeDao.getAll().indexOf(mTypeDao.findType(fixture.type))
        val statusIdx = mStatusDao.getAll().indexOf(mStatusDao.findStatus(fixture.status))
        val unitIdx = mUnitDao.getAll().indexOf(mUnitDao.findUnit(fixture.unit))

        // ??????
        val spinnerType = binding.editSpinnerType
        spinnerType.adapter = typeAdapter
        spinnerType.setSelection(typeIdx)
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
        spinnerStatus.setSelection(statusIdx)
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
        spinnerUnit.setSelection(unitIdx)
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

        binding.editFixtureButton.setOnClickListener { view : View ->
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
                    val newFixture = mFixtureDao.findFixture(args.selectedId)
                    newFixture.name = name
                    newFixture.type = type
                    newFixture.status = status
                    newFixture.quantity = quantity
                    newFixture.unit = unit
                    mFixtureDao.update(newFixture)
                    Snackbar.make(view, "${name}?????????????????????", Snackbar.LENGTH_SHORT).show();
                    val action = FixtureEditFragmentDirections.actionFixtureEditFragmentToFixtureShowFragment(args.selectedId)
                    view.findNavController().navigate(action)
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