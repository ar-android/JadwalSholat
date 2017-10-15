package com.ahmadrosid.jadwalsholat.api

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by ocittwo on 10/14/17.
 */

object JadwalSholatApi {

    const val URL = "https://jadwal-sholats.herokuapp.com/"

    class City {

        /** City name */
        var cityName: String? = null

        /** City name */
        var cityId: String? = null

    }

    class Jadwal{

        /** City name */
        var kota: String? = null

        /** Month */
        var bulan: String? = null

        /** Date */
        var tanggal: String? = null

        /** Shubuh */
        var shubuh: String? = null

        /** Dzuhur */
        var dzuhur: String? = null

        /** Ashr */
        var ashr: String? = null

        /** Maghrib */
        var maghrib: String? = null

        /** Isya */
        var isya: String? = null
    }

    interface Service{

        @GET("/")
        fun getDefaultJadwal() : Call<Jadwal>

        @GET("/api/v1/city")
        fun getAllCity() : Call<List<City>>

        @GET("/api/v1/city/{cityId}")
        fun getJadwalByCityId(@Path("cityId") cityId: String ) : Call<Jadwal>

    }

    private val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(MoshiConverterFactory.create()).build()

    val service = retrofit.create(Service::class.java)!!

}