package com.ahmadrosid.jadwalsholat.core.home

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.ahmadrosid.jadwalsholat.R
import com.ahmadrosid.jadwalsholat.api.JadwalSholatApi
import com.ahmadrosid.jadwalsholat.core.kota.KotaActivity
import com.ahmadrosid.jadwalsholat.utils.open
import com.ahmadrosid.jadwalsholat.utils.toast
import kotlinx.android.synthetic.main.activity_home.*

/**
 * Created by ocittwo on 10/15/17.
 */
class HomeActivity : AppCompatActivity(), LifecycleOwner{

    private var viewModel = HomeViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.loading.observe(this, Observer { displayLoading(it ?: false) })
        viewModel.message.observe(this, Observer { displayMessage( it ) })
        viewModel.jadwal.observe(this, Observer { displayData(it) })

    }

    private fun displayData(jadwal: JadwalSholatApi.Jadwal?) {
        shubuh.text = jadwal?.shubuh
        dzuhur.text = jadwal?.dzuhur
        ashar.text = jadwal?.ashr
        maghrib.text = jadwal?.maghrib
        isya.text = jadwal?.isya
        supportActionBar?.title = jadwal?.kota
    }

    private fun displayMessage(messages: String?) {
        toast(messages!!)
    }

    private fun displayLoading(loading: Boolean) {
        progress.visibility = if (loading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when {
            item?.itemId == R.id.changeCity -> open(KotaActivity::class.java)
        }
        return super.onOptionsItemSelected(item)
    }
}