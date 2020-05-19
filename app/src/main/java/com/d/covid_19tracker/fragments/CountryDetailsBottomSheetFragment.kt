package com.d.covid_19tracker.fragments

import android.graphics.Color
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.d.covid_19tracker.R
import com.d.covid_19tracker.models.Country
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.text.NumberFormat
import java.util.*

class CountryDetailsBottomSheetFragment(country: Country) : BottomSheetDialogFragment() {
    private val country: Country?

    init {
        this.country = country
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.coutnry_details_sheet, container, false)
        val tvCountryName: TextView = view.findViewById(R.id.tvCountryName)
        val tvUpdated: TextView = view.findViewById(R.id.tvUpdated)
        val tvCases: TextView = view.findViewById(R.id.tvCases)
        val tvActive: TextView = view.findViewById(R.id.tvActive)
        val tvRecovered: TextView = view.findViewById(R.id.tvRecovered)
        val tvDeaths: TextView = view.findViewById(R.id.tvDeaths)
        val pieChart: PieChart = view.findViewById(R.id.pieChartGlobal)

        pieChart.description.isEnabled = false
        pieChart.isDrawHoleEnabled = true
        pieChart.setDrawEntryLabels(false)
        pieChart.setHoleColor(Color.WHITE)

        val yValues: MutableList<PieEntry> = ArrayList()

        yValues.add(PieEntry(country!!.getCases()!!.toFloat(), "CASES"))
        yValues.add(PieEntry(country!!.getDeaths()!!.toFloat(), "DEATHS"))
        yValues.add(PieEntry(country!!.getActive()!!.toFloat(), "ACTIVE"))
        yValues.add(PieEntry(country!!.getRecovered()!!.toFloat(), "RECOVERDE"))

        val dataSet = PieDataSet(yValues, "")
        dataSet.setColors(
            Color.parseColor("#C9302C"),
            Color.parseColor("#666666"),
            Color.parseColor("#FF9C00"),
            Color.parseColor("#337AB7")
        )
        val data = PieData(dataSet)
        data.setValueTextSize(10f)
        data.setValueTextColor(Color.BLACK)
        pieChart.setData(data)
        pieChart.invalidate()

        val localeEN = Locale("en", "EN")
        val en = NumberFormat.getInstance(localeEN)

        tvCases.text = en.format(country.getCases()!!.toLong())
        tvActive.text = en.format(country.getActive()!!.toLong())
        tvRecovered.text = en.format(country.getRecovered()!!.toLong())
        tvDeaths.text = en.format(country.getDeaths()!!.toLong())
        tvUpdated.text = timeStampToString(country.getUpdated()!!.toLong())
        tvCountryName.text = country.getCountry()

        return view
    }

    private fun timeStampToString(time: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = time
        return DateFormat.format("dd/MM/yyyy hh:mm:ss", calendar).toString()
    }
}