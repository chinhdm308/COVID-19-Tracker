package com.d.covid_19tracker.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.d.covid_19tracker.R
import com.d.covid_19tracker.adapters.CountryAdapter
import com.d.covid_19tracker.models.Country
import com.leo.simplearcloader.SimpleArcLoader
import kotlinx.android.synthetic.main.coutnry_details_sheet.view.*
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class CountryFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_country, container, false)
        val loader: SimpleArcLoader = view.findViewById(R.id.loader)
        val recyclerViewCountry: RecyclerView = view.findViewById(R.id.recyclerViewCountry)
        val editTextCountrySearch: EditText = view.findViewById(R.id.editTextCountrySearch)

        val countries: ArrayList<Country> = ArrayList()

        loader.visibility = View.VISIBLE
        recyclerViewCountry.visibility = View.INVISIBLE
        loader.start()

        editTextCountrySearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val countriesSearch: ArrayList<Country> = ArrayList()
                for (i in countries) {
                    if (i.getCountry()!!.toLowerCase(Locale.ROOT)
                            .contains(s.toString().toLowerCase(Locale.ROOT))
                    ) {
                        countriesSearch.add(i)
                    }
                }

                val countryAdapter = CountryAdapter(context!!, activity!!, countriesSearch)
                recyclerViewCountry.setHasFixedSize(true)
                recyclerViewCountry.setLayoutManager(LinearLayoutManager(context))
                recyclerViewCountry.setAdapter(countryAdapter)
            }

            override fun afterTextChanged(s: Editable) {}
        })

        val url = "https://disease.sh/v2/countries"
        val jsonArrayRequest =
            JsonArrayRequest(
                Request.Method.GET, url, null,
                Response.Listener { response ->
                    var country: Country
                    for (i in 0 until response.length()) {
                        try {
                            var ob: JSONObject = response.getJSONObject(i)
                            country = Country()
                            country.setUpdate(ob.getString("updated"))
                            country.setCountry(ob.getString("country"))
                            var countryInfo: JSONObject = ob.getJSONObject("countryInfo")
                            country.setFlag(countryInfo.getString("flag"))
                            country.setCases(ob.getString("cases"))
                            country.setDeaths(ob.getString("deaths"))
                            country.setRecovered(ob.getString("recovered"))
                            country.setActive(ob.getString("active"))
                            countries.add(country)
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }

                    Collections.sort(countries, kotlin.Comparator
                    { o1, o2 ->
                        o2.getCases()!!.toFloat().compareTo(o1.getCases()!!.toFloat())
                    })

                    val countryAdapter = CountryAdapter(context!!, activity!!, countries)
                    recyclerViewCountry.setHasFixedSize(true)
                    recyclerViewCountry.layoutManager = LinearLayoutManager(context)
                    recyclerViewCountry.adapter = countryAdapter

                    loader.stop()
                    recyclerViewCountry.visibility = View.VISIBLE
                    loader.visibility = View.INVISIBLE
                }, Response.ErrorListener { error -> error.message })
        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(jsonArrayRequest)

        return view
    }
}
