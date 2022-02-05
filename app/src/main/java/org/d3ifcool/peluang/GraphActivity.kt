package org.d3ifcool.peluang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class GraphActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)
    }
}

//Coming Soon
//
//package org.d3ifcool.peluang
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.databinding.DataBindingUtil
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProvider
//import androidx.navigation.findNavController
//import androidx.navigation.ui.navigateUp
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.github.mikephil.charting.components.Legend
//import com.github.mikephil.charting.components.XAxis
//import com.github.mikephil.charting.data.Entry
//import com.github.mikephil.charting.data.LineData
//import com.github.mikephil.charting.data.LineDataSet
//import com.github.mikephil.charting.highlight.Highlight
//import com.github.mikephil.charting.listener.OnChartValueSelectedListener
//import kotlinx.android.synthetic.main.fragment_graph.*
//import org.d3ifcool.peluang.database.Cash
//import org.d3ifcool.peluang.database.CashDatabase
//import org.d3ifcool.peluang.databinding.FragmentGraphBinding
//import org.d3ifcool.peluang.recyclerview.CashAdapter
//import org.d3ifcool.peluang.viewmodel.CashViewModel
//
//class GraphFragment : Fragment() {
//    private lateinit var binding: FragmentGraphBinding
//    private lateinit var viewModel: CashViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_graph, container, false)
//        val application = requireNotNull(this.activity).application
//        val dataSource = CashDatabase.getInstance(application).CashDao
//        val viewModelFactory = CashViewModel.Factory(dataSource)
//        viewModel = ViewModelProvider(this, viewModelFactory).get(CashViewModel::class.java)
//
//        binding.lifecycleOwner = this
//        setHasOptionsMenu(true)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        viewModel.cashOut.observe(viewLifecycleOwner, {
//            val cashAdapter = CashAdapter(it)
//            val recyclerView = binding.rvCash
//
//            recyclerView.apply {
//                this.adapter = cashAdapter
//                this.layoutManager = LinearLayoutManager(requireContext())
//            }
//        })
//
//        viewModel.getData().observe(viewLifecycleOwner, {
//            val cashOut: List<Cash>? = viewModel.cashOut.value
//            if (cashOut != null) {
//                CashAdapter(cashOut).setData(cashOut)
//            }
//        })
//
//        viewModel.getEntries().observe(viewLifecycleOwner, { updateChart(it) })
//
//        with(chart) {
//            setNoDataText(getString(R.string.belum_ada_data))
//            description.text = ""
//            xAxis.position = XAxis.XAxisPosition.BOTTOM
//            axisLeft.axisMinimum = 0f
//            axisRight.isEnabled = false
//            legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
//            legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
//            legend.setDrawInside(false)
//        }
//
//        chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
//            override fun onValueSelected(entry: Entry?, highlight: Highlight) {
//                rv_cash.scrollToPosition(highlight.x.toInt())
//            }
//
//            override fun onNothingSelected() {}
//        })
//    }
//
//    private fun updateChart(entries: List<Entry>?) {
//        val datasetCashOut = LineDataSet(entries, getString(R.string.cash_out))
//        datasetCashOut.color = R.color.cashOut
//        datasetCashOut.fillColor = datasetCashOut.color
//        datasetCashOut.setDrawFilled(false)
//        datasetCashOut.setDrawCircles(false)
//
//        chart.data = LineData(datasetCashOut)
//        chart.invalidate()
//    }
//}