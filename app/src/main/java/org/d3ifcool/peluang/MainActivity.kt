package org.d3ifcool.peluang

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import org.d3ifcool.peluang.database.Cash
import org.d3ifcool.peluang.database.CashDatabase
import org.d3ifcool.peluang.ui.*
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity(),
    MainDialogCashIn.DialogListener, MainDialogCashOut.DialogListener,
    MainDialogEditCashIn.DialogListener, MainDialogEditCashOut.DialogListener {
    companion object {
        var idCash = 0
        var nominal = 0.0
        var deskripsi = ""
    }

    private lateinit var adapter: MainAdapter
    private var actionMode: ActionMode? = null

    private val actionModeCallback = object : ActionMode.Callback {
        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
            when (item?.itemId) {
                R.id.delete -> {
                    deleteData()
                    return true
                }

                R.id.edit -> {
                    if (adapter.getSelection().size == 1) {
                        for (id in adapter.getSelection()) {
                            idCash = id
                        }

                        var list: List<Cash> = arrayListOf()
                        viewModel.cashAll.observe(this@MainActivity, { list = it })

                        for (id in list.indices) {
                            if (list[id].id == idCash) {
                                nominal = list[id].nominal
                                deskripsi = list[id].deskripsi
                            }
                        }

                        if (nominal > 0) {
                            MainDialogEditCashIn().show(supportFragmentManager, "MainDialogEdit")
                        } else {
                            nominal = -nominal
                            MainDialogEditCashOut().show(supportFragmentManager, "MainDialogEdit")
                        }
                    }

                    return true
                }
                else -> return false
            }
        }

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.menuInflater?.inflate(R.menu.delete, menu)
            if (adapter.getSelection().size == 1)
                mode?.menuInflater?.inflate(R.menu.edit, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            mode?.title = adapter.getSelection().size.toString()
            return true
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            actionMode = null
            adapter.resetSelection()
        }
    }

    private val handler = object : MainAdapter.ClickHandler {
        override fun onLongClick(position: Int): Boolean {
            if (actionMode != null) return false
            adapter.toggleSelection(position)
            actionMode = startSupportActionMode(actionModeCallback)
            return true
        }

        override fun onClick(position: Int, cash: Cash) {
            if (actionMode != null) {
                adapter.toggleSelection(position)
                if (adapter.getSelection().isEmpty())
                    actionMode?.finish()
                else
                    actionMode?.invalidate()
                return
            }
        }
    }

    private fun deleteData() {
        val builder = AlertDialog.Builder(this)
            .setTitle(R.string.peringatan)
            .setMessage(R.string.delete_cashout_confirmation)
            .setPositiveButton("Hapus") { _, _ ->
                viewModel.deleteData(adapter.getSelection())
                actionMode?.finish()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.cancel()
                actionMode?.finish()
            }
        builder.show()
    }

    private fun resetData() {
        val builder = AlertDialog.Builder(this)
            .setTitle(R.string.peringatan)
            .setMessage(R.string.reset_data_confirmation)
            .setPositiveButton("Hapus") { _, _ ->
                viewModel.resetData()
                actionMode?.finish()
            }
            .setNegativeButton("Batal") { dialog, _ ->
                dialog.cancel()
                actionMode?.finish()
            }
        builder.show()
    }

    private val viewModel: MainViewModel by lazy {
        val dataSource = CashDatabase.getInstance(this).dao
        val factory = MainViewModelFactory(dataSource)
        ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.nominal.observe(this, {
            val nominal = viewModel.nominal.value
            var saldo = 0.00

            for (i in nominal?.indices!!) {
                saldo += nominal[i]
            }

            val formattedSaldo =
                NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(saldo)
            tvSaldo.text = formattedSaldo

            when {
                saldo > 0.00 -> {
                    tvSaldo.setTextColor(Color.parseColor("#00CC00"))
                }
                saldo == 0.00 -> {
                    tvSaldo.setTextColor(Color.parseColor("#0000CC"))
                }
                else -> {
                    tvSaldo.setTextColor(Color.parseColor("#CC0000"))
                }
            }
        })

        btnCashIn.setOnClickListener {
            MainDialogCashIn().show(supportFragmentManager, "MainDialogCash")
        }

        btnCashOut.setOnClickListener {
            MainDialogCashOut().show(supportFragmentManager, "MainDialogCash")
        }

        adapter = MainAdapter(handler)
        val itemDecor = DividerItemDecoration(this, RecyclerView.VERTICAL)
        rv_cash.addItemDecoration(itemDecor)
        rv_cash.adapter = adapter
        viewModel.cashAll.observe(this, {
            adapter.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.reset, menu)

        //Coming Soon
//        inflater.inflate(R.menu.graph, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.reset -> {
                resetData()
                true
            }
            else -> true
        }
    }

    override fun processDialog(cash: Cash) {
        viewModel.insertData(cash)
    }

    override fun processDialogEdit(cash: Cash) {
        viewModel.updateData(cash)
    }
}