package com.ahmadrosid.jadwalsholat.core.kota

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.ahmadrosid.jadwalsholat.R
import com.ahmadrosid.jadwalsholat.api.JadwalSholatApi.City
import com.ahmadrosid.jadwalsholat.core.detail.DetailJadwalActivity
import com.ahmadrosid.jadwalsholat.utils.inflate
import kotlinx.android.synthetic.main.item_city.view.*

/**
 * Created by ocittwo on 10/14/17.
 */
class CityAdapter : RecyclerView.Adapter<CityAdapter.Holder>() {

    var city = emptyList<City>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = Holder(parent.inflate(R.layout.item_city))

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item  = city[position]
        val context = holder.itemView.context
        holder.itemView.cityName.text = item.cityName
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailJadwalActivity::class.java)
            intent.putExtra("cityId", item.cityId!!)
            context.startActivity(intent)
        }
    }

    override fun getItemCount() = city.size

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)

}