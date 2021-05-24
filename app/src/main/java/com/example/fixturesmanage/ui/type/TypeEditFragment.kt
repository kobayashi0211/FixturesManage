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
import androidx.navigation.fragment.navArgs
import com.example.fixturesmanage.R
import com.example.fixturesmanage.dao.TypeDao
import com.example.fixturesmanage.database.FixturesManageDatabase
import com.example.fixturesmanage.databinding.EditTypeFragmentBinding
import com.google.android.material.snackbar.Snackbar

class TypeEditFragment : Fragment() {

    companion object {
        fun newInstance() = TypeEditFragment()
    }

//    private lateinit var viewModel: TypeShowViewModel
    lateinit var mTypeDao: TypeDao
    private val args: TypeShowFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mTypeDao = FixturesManageDatabase.getInstance(this.requireContext()).typeDao()
        val binding = DataBindingUtil.inflate<EditTypeFragmentBinding>(inflater, R.layout.edit_type_fragment, container, false)

        // 表示するモデルを取得
        val type = mTypeDao.findType(args.selectedId)
        binding.typeName = type.name

        binding.editTypeButton.setOnClickListener { view : View ->
            val editText = binding.editTextName
            val name = editText.text.toString()

            /* ボタンクリックのタイミングでキーボードを閉じる */
            val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            if(name.isNotBlank()){
                val newType = mTypeDao.findType(args.selectedId)
                newType.name = name
                mTypeDao.update(newType)
                Snackbar.make(view, "${name}を更新しました", Snackbar.LENGTH_SHORT).show();
                val action = TypeEditFragmentDirections.actionTypeEditFragmentToTypeShowFragment(args.selectedId)
                view.findNavController().navigate(action)
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