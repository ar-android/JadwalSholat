package com.ahmadrosid.jadwalsholat.data

/**
 * Created by ocittwo on 10/14/17.
 */
sealed class Action

data class LoadCity(val cityId: String) : Action()

data class Loadjadwal(val cityId: String) : Action()