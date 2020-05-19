package com.d.covid_19tracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.d.covid_19tracker.R
import com.d.covid_19tracker.fragments.CountryDetailsBottomSheetFragment
import com.d.covid_19tracker.models.Country

class CountryAdapter(mContext: Context, fragmentActivity: FragmentActivity, mData: ArrayList<Country>) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    private val mContext: Context?
    private val mData: ArrayList<Country>?
    private val fragmentActivity: FragmentActivity?

    init {
        this.mContext = mContext
        this.mData = mData
        this.fragmentActivity = fragmentActivity
    }

    public class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val flag: ImageView = itemView.findViewById(R.id.flag)
        val tvCountry: TextView = itemView.findViewById(R.id.country)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.row_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData!!.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = mData!![position]

        Glide.with(mContext!!).load(country.getFlag()).into(holder.flag)
        holder.tvCountry.text = country.getCountry()

        holder.itemView.setOnClickListener {
            val bottomSheetFragment = CountryDetailsBottomSheetFragment(country)
            bottomSheetFragment.show(fragmentActivity!!.supportFragmentManager, "Bottom Sheet")
        }
    }
}