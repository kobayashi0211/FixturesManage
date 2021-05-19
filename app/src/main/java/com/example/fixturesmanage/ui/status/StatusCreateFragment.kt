package com.example.fixturesmanage.ui.status

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
import com.example.fixturesmanage.dao.StatusDao
import com.example.fixturesmanage.database.FixturesManageDatabase
import com.example.fixturesmanage.databinding.CreateStatusFragmentBinding
import com.example.fixturesmanage.model.Status
import com.google.android.material.snackbar.Snackbar

class StatusCreateFragment : Fragment() {

    companion object {
        fun newInstance() = StatusCreateFragment()
    }

//    private lateinit var viewModel: CreateStatusViewModel
    lateinit var mStatusDao: StatusDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mStatusDao = FixturesManageDatabase.getInstance(this.requireContext()).statusDao()
        val binding = DataBindingUtil.inflate<CreateStatusFragmentBinding>(inflater, R.layout.create_status_fragment, container, false)

        binding.createStatusButton.setOnClickListener { view : View ->
            val editText = binding.editTextName
            val name = editText.text.toString()

            /* ボタンクリックのタイミングでキーボードを閉じる */
            val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            if(name.isNotBlank()){

                if(mStatusDao.existName(name)){
                    Snackbar.make(view, "${name}は登録済みです", Snackbar.LENGTH_SHORT).show();
                }else{
                    val newStatus = Status(0, name)
                    mStatusDao.insert(newStatus)
                    Snackbar.make(view, "${name}を登録しました", Snackbar.LENGTH_SHORT).show();
                    view.findNavController().navigate(R.id.action_statusCreateFragment_to_nav_status)
                }
            }else{
                Snackbar.make(view, "「状態名」を入力してください", Snackbar.LENGTH_SHORT).show();
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

}