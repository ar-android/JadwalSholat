package com.ahmadrosid.jadwalsholat.utils

import android.content.Context
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.suspendCoroutine

/**
 * Created by ocittwo on 10/14/17.
 */

@Suppress("unused") val Any?.unit get() = Unit

operator fun StringBuilder.plusAssign(string: String) = append(string).unit

fun <T> List<T>.changes(destination: MutableList<Pair<T, T>> = ArrayList(size))
        : MutableList<Pair<T, T>> {
    for (i in 0..size - 2)
        destination += get(i) to get(i + 1)
    return destination
}

suspend fun <T> Call<T>.await(): T = suspendCoroutine { continuation ->

    val callback = object : Callback<T> {
        override fun onFailure(call: Call<T>, t: Throwable) = continuation.resumeWithException(t)
        override fun onResponse(call: Call<T>, response: Response<T>)
                = continuation.resumeNormallyOrWithException {
            response.isSuccessful || throw IllegalStateException("Http error ${response.code()}")
            response.body() ?: throw IllegalStateException("Response body is null")
        }
    }

    enqueue(callback) // For cancellation (invoke Call.cancel() when coroutine is cancelled)
}

private inline fun <T> Continuation<T>.resumeNormallyOrWithException(getter: () -> T) = try {
    val result = getter()
    resume(result)
} catch (exception: Throwable) {
    resumeWithException(exception)
}

fun Context.toast(message: CharSequence) { Toast.makeText(this, message, Toast.LENGTH_SHORT).show() }
