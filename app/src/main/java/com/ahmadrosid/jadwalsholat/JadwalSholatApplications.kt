package com.ahmadrosid.jadwalsholat

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by ocittwo on 10/15/17.
 */
class JadwalSholatApplications : Application(){

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        val config = RealmConfiguration.Builder().name("myrealm.realm").build()
        Realm.setDefaultConfiguration(config)
    }

}