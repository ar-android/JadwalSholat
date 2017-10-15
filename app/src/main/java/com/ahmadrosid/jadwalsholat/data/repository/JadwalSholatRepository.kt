package com.ahmadrosid.jadwalsholat.data.repository

import com.ahmadrosid.jadwalsholat.api.JadwalSholatApi
import com.ahmadrosid.jadwalsholat.api.JadwalSholatApi.City
import com.ahmadrosid.jadwalsholat.utils.await

/**
 * Created by ocittwo on 10/14/17.
 */

object JadwalSholatRepository{

    suspend fun getAllCity(): List<City>
            = JadwalSholatApi.service.getAllCity().await()

    suspend fun getJadwalByCityId(cityId: String)
            = JadwalSholatApi.service.getJadwalByCityId(cityId).await()

    suspend fun getJadwalDefault()
            = JadwalSholatApi.service.getDefaultJadwal().await()

}