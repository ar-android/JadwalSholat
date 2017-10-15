package com.ahmadrosid.jadwalsholat.core.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ahmadrosid.jadwalsholat.api.JadwalSholatApi
import com.ahmadrosid.jadwalsholat.data.Action
import com.ahmadrosid.jadwalsholat.data.LoadCity
import com.ahmadrosid.jadwalsholat.data.repository.JadwalSholatRepository
import com.ahmadrosid.jadwalsholat.utils.unit
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.actor

/**
 * Created by ocittwo on 10/15/17.
 */
class DetailJadwalViewModel : ViewModel(){

    private val mutableJadwal = MutableLiveData<JadwalSholatApi.Jadwal>().apply { value = null }
    private val mutableLoading = MutableLiveData<Boolean>().apply { value = false }
    private val mutableMessage = MutableLiveData<String>()

    val jadwal: LiveData<JadwalSholatApi.Jadwal> = mutableJadwal
    val loading: LiveData<Boolean> = mutableLoading
    val message: LiveData<String> = mutableMessage

    private val actor = actor<Action>(UI, Channel.CONFLATED) {
        for (action in this) when (action) {
            is LoadCity -> {
                mutableLoading.value = true
                try {
                    mutableJadwal.value = getJadwal(action.cityId)
                } catch (e: Exception) {
                    mutableMessage.value = e.message.toString()
                }
                mutableLoading.value = false
            }
        }
    }

    private suspend fun getJadwal(cityId: String): JadwalSholatApi.Jadwal =
            JadwalSholatRepository.getJadwalByCityId(cityId)

    fun loadCityById(cityId: String){
        action(LoadCity(cityId))
    }

    private fun action(action: Action) = actor.offer(action)

    override fun onCleared() = actor.cancel().unit

}