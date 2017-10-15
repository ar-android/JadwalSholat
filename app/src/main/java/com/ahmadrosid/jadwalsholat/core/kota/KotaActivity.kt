package com.ahmadrosid.jadwalsholat.core.kota

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.View
import com.ahmadrosid.jadwalsholat.R
import com.ahmadrosid.jadwalsholat.api.JadwalSholatApi.City
import com.ahmadrosid.jadwalsholat.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import android.support.v7.widget.SearchView

class KotaActivity : AppCompatActivity(), LifecycleOwner, SearchView.OnQueryTextListener {

    private val registry = LifecycleRegistry(this)

    override fun getLifecycle() = registry

    private lateinit var viewModel: KotaViewModel

    private val adapter = CityAdapter()

    var cityList : List<City> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listCity.adapter = adapter
        listCity.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProviders.of(this).get(KotaViewModel::class.java)
        viewModel.loading.observe(this, Observer { displayLoading(it ?: false) })
        viewModel.city.observe(this, Observer { displayCity(it!!) })
        viewModel.message.observe(this, Observer { displayMessage(it ?: "") })

    }

    private fun displayCity(city: List<City>) {
        adapter.city = city
        cityList = city
    }

    private fun displayLoading(loading: Boolean) {
        progress.visibility = if (loading) View.VISIBLE else View.INVISIBLE
    }

    private fun displayMessage(message: String) {
        if (message.isNotBlank()) toast(message)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val item = menu?.findItem(R.id.action_search)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean = false

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText?.length!! > 1){
            search(newText)
        }
        return true
    }

    private fun search(newText: String) {
        val query = newText.toLowerCase()
        var filteredCity : List<City> = emptyList()
        cityList.forEach {
            val text = it.cityName?.toLowerCase()
            if (text!!.contains(query))
                filteredCity += it
        }
        adapter.city = filteredCity
    }

}
