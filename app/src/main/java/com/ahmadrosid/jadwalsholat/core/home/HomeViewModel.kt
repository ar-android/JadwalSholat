package com.ahmadrosid.jadwalsholat.core.home

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ahmadrosid.jadwalsholat.api.JadwalSholatApi.Jadwal
import com.ahmadrosid.jadwalsholat.data.Action
import com.ahmadrosid.jadwalsholat.data.Loadjadwal
import com.ahmadrosid.jadwalsholat.data.repository.JadwalSholatRepository
import com.ahmadrosid.jadwalsholat.utils.unit
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.Channel
import kotlinx.coroutines.experimental.channels.actor

/**
 * Created by ocittwo on 10/15/17.
 */
class HomeViewModel: ViewModel(){

    private var mutableJadwal = MutableLiveData<Jadwal>().apply{ value = null }
    private var mutableLoading = MutableLiveData<Boolean>().apply{ value = false }
    private var mutableMessage = MutableLiveData<String>().apply{ value = "" }

    var jadwal :LiveData<Jadwal> = mutableJadwal
    var loading :LiveData<Boolean> = mutableLoading
    var message :LiveData<String> = mutableMessage

    private val actor = actor<Action>(UI, Channel.CONFLATED) {
        for (action in this) when (action) {
            is Loadjadwal -> {

                mutableLoading.value = true
                try {
                    mutableJadwal.value = JadwalSholatRepository.getJadwalDefault()
                } catch (e: Exception) {
                    mutableMessage.value = e.message.toString()
                }
                mutableLoading.value = false

            }
        }
    }

    init {
        action(Loadjadwal("0"))
    }

    private fun action(action: Action) = actor.offer(action)

    override fun onCleared() = actor.cancel().unit
}