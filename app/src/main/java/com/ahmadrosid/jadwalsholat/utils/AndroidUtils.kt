package com.ahmadrosid.jadwalsholat.utils

import android.content.Context
import android.content.Intent
import android.support.annotation.LayoutRes
import android.view.ViewGroup

/**
 * Created by ocittwo on 10/14/17.
 */


fun ViewGroup.inflate(@LayoutRes layoutId: Int, attachToRoot: Boolean = false): android.view.View {
    val inflater = android.view.LayoutInflater.from(context)
    return inflater.inflate(layoutId, this, attachToRoot)
}

fun Context.open(clzz: Class<*>){
    startActivity(Intent(this, clzz))
}