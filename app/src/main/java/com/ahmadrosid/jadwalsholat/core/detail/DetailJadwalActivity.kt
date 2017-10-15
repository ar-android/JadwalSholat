package com.ahmadrosid.jadwalsholat.core.detail

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.ahmadrosid.jadwalsholat.R
import com.ahmadrosid.jadwalsholat.api.JadwalSholatApi
import kotlinx.android.synthetic.main.activity_jadwal_detail.*

/**
 * Created by ocittwo on 10/15/17.
 */
class DetailJadwalActivity : AppCompatActivity(), LifecycleOwner{

    private lateinit var viewModel: DetailJadwalViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jadwal_detail)

        viewModel = ViewModelProviders.of(this).get(DetailJadwalViewModel::class.java)
        viewModel.loadCityById(intent.getStringExtra("cityId"))
        viewModel.loading.observe(this, Observer { displayLoading(it ?: false) })
        viewModel.jadwal.observe(this, Observer { it?.let { it1 -> displayData(it1) } })

    }

    private fun displayLoading(loading: Boolean) {
        progress.visibility = if (loading) View.VISIBLE else View.INVISIBLE
        content.visibility = if (!loading) View.VISIBLE else View.INVISIBLE
    }

    private fun displayData(jadwal: JadwalSholatApi.Jadwal) {
        shubuh.text = jadwal.shubuh
        dzuhur.text = jadwal.dzuhur
        ashar.text = jadwal.ashr
        maghrib.text = jadwal.maghrib
        isya.text = jadwal.isya
        supportActionBar?.title = jadwal.kota
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home){
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}