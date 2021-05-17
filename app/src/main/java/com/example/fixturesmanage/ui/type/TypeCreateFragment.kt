package com.example.fixturesmanage.ui.type

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
import com.example.fixturesmanage.dao.TypeDao
import com.example.fixturesmanage.database.TypeDatabase
import com.example.fixturesmanage.databinding.CreateTypeFragmentBinding
import com.example.fixturesmanage.model.Type
import com.google.android.material.snackbar.Snackbar

class TypeCreateFragment : Fragment() {

    companion object {
        fun newInstance() = TypeCreateFragment()
    }

//    private lateinit var viewModel: TypeShowViewModel
    lateinit var mTypeDao: TypeDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mTypeDao = TypeDatabase.getInstance(this.requireContext()).typeDao()
        val binding = DataBindingUtil.inflate<CreateTypeFragmentBinding>(inflater, R.layout.create_type_fragment, container, false)

        binding.createTypeButton.setOnClickListener { view : View ->
            val editText = binding.editTextName
            val name = editText.text.toString()

            /* ボタンクリックのタイミングでキーボードを閉じる */
            val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            if(name.isNotBlank()){
                if(mTypeDao.existName(name)){
                    Snackbar.make(view, "${name}は登録済みです", Snackbar.LENGTH_SHORT).show();
                }else{
                    val newType = Type(0, name)
                    mTypeDao.insert(newType)
                    Snackbar.make(view, "${name}を登録しました", Snackbar.LENGTH_SHORT).show();
                    view.findNavController().navigate(R.id.action_typeCreateFragment_to_nav_type)
                }
            }else{
                Snackbar.make(view, "「種別名」を入力してください", Snackbar.LENGTH_SHORT).show();
            }
        }

        setHasOptionsMenu(true)
        return binding.root
//        return inflater.inflate(R.layout.create_type_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProvider(this).get(TypeShowViewModel::class.java)
        // TODO: Use the ViewModel
    }

}