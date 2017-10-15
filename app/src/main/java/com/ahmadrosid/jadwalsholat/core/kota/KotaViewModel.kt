package com.ahmadrosid.jadwalsholat.core.kota

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.ahmadrosid.jadwalsholat.api.JadwalSholatApi.City

import android.arch.lifecycle.ViewModel
import com.ahmadrosid.jadwalsholat.data.Action
import com.ahmadrosid.jadwalsholat.data.LoadCity
import com.ahmadrosid.jadwalsholat.data.repository.JadwalSholatRepository
import com.ahmadrosid.jadwalsholat.utils.unit
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.actor

/**
 * Created by ocittwo on 10/14/17.
 */
class KotaViewModel : ViewModel() {

    private val mutableCity = MutableLiveData<List<City>>().apply { value = emptyList() }
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val mutableMessage = MutableLiveData<String>()

    val city: LiveData<List<City>> = mutableCity
    val loading: LiveData<Boolean> = mutableLoading
    val message: LiveData<String> = mutableMessage

    private val actor = actor<Action>(UI, Channel.CONFLATED) {
        mutableLoading.value = true
        try {
            mutableCity.value = getCityList()
        } catch (e: Exception) {
            mutableMessage.value = e.message.toString()
        }
        mutableLoading.value = false
    }

    init {
        action(LoadCity("0"))
    }

    override fun onCleared() = actor.cancel().unit

    private fun action(action: Action) = actor.offer(action)

    private suspend fun getCityList(): List<City> {
        val city = JadwalSholatRepository.getAllCity()
        city.size > 1 || return emptyList()
        return city
    }
}