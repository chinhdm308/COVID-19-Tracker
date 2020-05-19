package com.d.covid_19tracker.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.d.covid_19tracker.R
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.leo.simplearcloader.SimpleArcLoader
import org.json.JSONException
import java.text.NumberFormat
import java.util.*


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        val loader: SimpleArcLoader = view.findViewById(R.id.loader)
        val showLayout: RelativeLayout = view.findViewById(R.id.showLayout)
        val pieChart: PieChart = view.findViewById(R.id.pieChartGlobal)
        val tvUpdated: TextView = view.findViewById(R.id.tvUpdated)
        val tvCases: TextView = view.findViewById(R.id.tvCases)
        val tvActive: TextView = view.findViewById(R.id.tvActive)
        val tvDeaths: TextView = view.findViewById(R.id.tvDeaths)
        val tvRecovered: TextView = view.findViewById(R.id.tvRecovered)

        loader.visibility = View.VISIBLE
        showLayout.visibility = View.INVISIBLE
        loader.start()

        pieChart.description.isEnabled = false
        pieChart.isDrawHoleEnabled = true
        pieChart.setDrawEntryLabels(false)
        pieChart.setHoleColor(Color.WHITE)

        val yValues: ArrayList<PieEntry> = ArrayList()

        val requestQueue: RequestQueue = Volley.newRequestQueue(context)
        val url: String = "https://disease.sh/v2/all"
        val jsonObjectRequest: JsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            Response.Listener { response ->
                try {
                    yValues.add(PieEntry(response.getString("cases").toFloat(), "CASES"))
                    yValues.add(PieEntry(response.getString("deaths").toFloat(), "DEATHS"))
                    yValues.add(PieEntry(response.getString("active").toFloat(), "ACTIVE"))
                    yValues.add(PieEntry(response.getString("recovered").toFloat(), "RECOVERDE"))

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
                    pieChart.data = data

                    val localeEN = Locale("en", "EN")
                    val en = NumberFormat.getInstance(localeEN)
                    tvCases.text = en.format(response.getLong("cases"))
                    tvActive.text = en.format(response.getLong("active"))
                    tvRecovered.text = en.format(response.getLong("recovered"))
                    tvDeaths.text = en.format(response.getLong("deaths"))
                    tvUpdated.text = timeStampToString(response.getLong("updated"))

                    loader.stop()
                    loader.visibility = View.INVISIBLE
                    showLayout.visibility = View.VISIBLE
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, Response.ErrorListener { error ->
                error.message
            })
        requestQueue.add(jsonObjectRequest)

        return view
    }

    private fun timeStampToString(time: Long): String {
        val calendar = Calendar.getInstance(Locale.ENGLISH)
        calendar.timeInMillis = time
        return DateFormat.format("dd/MM/yyyy hh:mm:ss", calendar).toString()
    }

}
