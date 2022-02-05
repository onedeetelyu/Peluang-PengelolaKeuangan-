package org.d3ifcool.peluang.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_cash.view.*
import org.d3ifcool.peluang.R
import org.d3ifcool.peluang.database.Cash
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList

class MainAdapter(private val handler: ClickHandler) :
    ListAdapter<Cash, MainAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cash>() {
            override fun areItemsTheSame(oldData: Cash, newData: Cash): Boolean {
                return oldData.id == newData.id
            }

            override fun areContentsTheSame(oldData: Cash, newData: Cash): Boolean {
                return oldData == newData
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item_cash, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(cash: Cash) {
            if (cash.nominal > 0) {
                itemView.iv_category.setImageResource(R.drawable.addmoney)
                val formattedSaldo =
                    NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(cash.nominal)
                itemView.tv_nominal.text = formattedSaldo
            } else {
                itemView.iv_category.setImageResource(R.drawable.removemoney)
                val formattedSaldo =
                    NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(-cash.nominal)
                itemView.tv_nominal.text = formattedSaldo
            }
            itemView.tv_description.text = cash.deskripsi
            itemView.setOnLongClickListener { handler.onLongClick(adapterPosition) }
            itemView.isSelected = selectionIds.contains(cash.id)
            itemView.setOnClickListener { handler.onClick(adapterPosition, cash) }
        }
    }

    interface ClickHandler {
        fun onClick(position: Int, cash: Cash)
        fun onLongClick(position: Int): Boolean
    }

    private val selectionIds = ArrayList<Int>()
    fun toggleSelection(pos: Int) {
        val id = getItem(pos).id
        if (selectionIds.contains(id)) selectionIds.remove(id)
        else selectionIds.add(id)
        notifyDataSetChanged()
    }

    fun getSelection(): List<Int> {
        return selectionIds
    }

    fun resetSelection() {
        selectionIds.clear()
        notifyDataSetChanged()
    }
}