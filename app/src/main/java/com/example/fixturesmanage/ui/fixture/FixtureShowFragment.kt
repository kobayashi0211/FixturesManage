package com.example.fixturesmanage.ui.fixture

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
import com.example.fixturesmanage.dao.FixtureDao
import com.example.fixturesmanage.dao.StatusDao
import com.example.fixturesmanage.dao.TypeDao
import com.example.fixturesmanage.dao.UnitDao
import com.example.fixturesmanage.database.FixturesManageDatabase
import com.example.fixturesmanage.databinding.ShowFixtureFragmentBinding
import com.google.android.material.snackbar.Snackbar

class FixtureShowFragment : Fragment() {

    lateinit var mFixtureDao: FixtureDao
    lateinit var mTypeDao: TypeDao
    lateinit var mStatusDao: StatusDao
    lateinit var mUnitDao: UnitDao
    private val args: FixtureShowFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mFixtureDao = FixturesManageDatabase.getInstance(this.requireContext()).fixtureDao()
        mTypeDao = FixturesManageDatabase.getInstance(this.requireContext()).typeDao()
        mStatusDao = FixturesManageDatabase.getInstance(this.requireContext()).statusDao()
        mUnitDao = FixturesManageDatabase.getInstance(this.requireContext()).unitDao()

        val binding = DataBindingUtil.inflate<ShowFixtureFragmentBinding>(inflater, R.layout.show_fixture_fragment, container, false)

        // 表示するモデルを取得
        val fixture = mFixtureDao.findFixture(args.selectedId)

        binding.textViewName.text = fixture.name
        binding.textViewType.text = mTypeDao.findType(fixture.type).name
        binding.textViewStatus.text = mStatusDao.findStatus(fixture.status).name
        binding.textViewQuantity.text = fixture.quantity.toString()
        binding.textViewUnit.text = mUnitDao.findUnit(fixture.unit).name

        // 編集がクリックされた時のイベントリスナー
        binding.buttonEdit.setOnClickListener { view : View ->
            // 表示中のモデルID
            val id = args.selectedId
            // 生成されたクラスに引数を渡して遷移
            val action = FixtureShowFragmentDirections.actionFixtureShowFragmentToFixtureEditFragment(id.toString().toInt())
            view.findNavController().navigate(action)
        }

        binding.buttonDelete.setOnClickListener { view : View ->
            AlertDialog.Builder(this.requireContext()) // FragmentではActivityを取得して生成
                .setTitle("削除確認")
                .setMessage("${fixture.name} を削除しますか？")
                .setPositiveButton("OK") { dialog, which ->
                    mFixtureDao.delete(fixture)
                    Snackbar.make(view, "${fixture.name} を削除しました", Snackbar.LENGTH_SHORT).show();
                    view.findNavController().navigate(R.id.action_fixtureShowFragment_to_nav_fixture)
                }
                .setNegativeButton("No") { dialog, which -> /* 何もしない */ }
                .show()
        }

        setHasOptionsMenu(true)
        return binding.root
    }

}