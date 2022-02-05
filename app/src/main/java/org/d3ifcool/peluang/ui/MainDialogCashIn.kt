package org.d3ifcool.peluang.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import org.d3ifcool.peluang.R
import org.d3ifcool.peluang.database.Cash

class MainDialogCashIn : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.dialog_cash, null, false)
        val builder = AlertDialog.Builder(requireContext())

        with(builder) {
            setTitle("Tambah Pemasukan")
            setView(view)
            setPositiveButton("Simpan") { _, _ ->
                val cashIn = getData(view) ?: return@setPositiveButton
                val listener = requireActivity() as DialogListener
                listener.processDialog(cashIn)
            }
            setNegativeButton("Batal") { _, _ -> dismiss() }
        }
        return builder.create()
    }

    interface DialogListener {
        fun processDialog(cash: Cash)
    }

    private fun getData(view: View): Cash? {
        val nominalEditText = view.findViewById<EditText>(R.id.etNominal)
        val deskripsiEditText = view.findViewById<EditText>(R.id.etDeskripsi)

        if (nominalEditText.text.isEmpty()) {
            showMessage(R.string.nominal_null)
            return null
        }

        if (nominalEditText.text.toString().toDouble() <= 0.0) {
            showMessage(R.string.nominal_negatif)
            return null
        }

        if (deskripsiEditText.text.isEmpty()) {
            showMessage(R.string.deskripsi_null)
            return null
        }

        return Cash(
            nominal = nominalEditText.text.toString().toDouble(),
            deskripsi = deskripsiEditText.text.toString(),
        )
    }

    private fun showMessage(messageResId: Int) {
        val toast = Toast.makeText(requireContext(), messageResId, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }
}