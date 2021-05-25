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
import androidx.navigation.fragment.navArgs
import com.example.fixturesmanage.R
import com.example.fixturesmanage.dao.StatusDao
import com.example.fixturesmanage.database.FixturesManageDatabase
import com.example.fixturesmanage.databinding.EditStatusFragmentBinding
import com.google.android.material.snackbar.Snackbar

class StatusEditFragment : Fragment() {

    companion object {
        fun newInstance() = StatusEditFragment()
    }

    lateinit var mStatusDao: StatusDao
    private val args: StatusShowFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mStatusDao = FixturesManageDatabase.getInstance(this.requireContext()).statusDao()
        val binding = DataBindingUtil.inflate<EditStatusFragmentBinding>(inflater, R.layout.edit_status_fragment, container, false)

        // 表示するモデルを取得
        val status = mStatusDao.findStatus(args.selectedId)
        binding.statusName = status.name

        binding.editStatusButton.setOnClickListener { view : View ->
            val editText = binding.editTextName
            val name = editText.text.toString()

            /* ボタンクリックのタイミングでキーボードを閉じる */
            val inputManager = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

            if(name.isNotBlank()){
                if(mStatusDao.existName(name)){
                    Snackbar.make(view, "${name}は登録済みです", Snackbar.LENGTH_SHORT).show();
                }else{
                    val newStatus = mStatusDao.findStatus(args.selectedId)
                    newStatus.name = name
                    mStatusDao.update(newStatus)
                    Snackbar.make(view, "${name}を更新しました", Snackbar.LENGTH_SHORT).show();
                    val action = StatusEditFragmentDirections.actionStatusEditFragmentToStatusShowFragment(args.selectedId)
                    view.findNavController().navigate(action)
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