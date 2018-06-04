package com.custom.weather.extesions

import android.util.Log
import java.util.NoSuchElementException

fun <K, V : Any> Map<K, V?>.toVarargArray(): Array<out Pair<K, V>> =
        map { Pair(it.key, it.value!!) }.toTypedArray()

inline fun <T, R : Any> Iterable<T>.firstResult(predicate: (T) -> R?): R{
    for(element in this){

        val result = predicate(element)
        Log.i("firstResult","firstResult = ${result} elementname = ${element.toString()}")
        if(result != null) {Log.i("firstResult", "firstResult else0")
            return result} else Log.i("firstResult", "firstResult else")
    }
    Log.i("firstResult", "firstResult else111")
    throw NoSuchElementException("No element matching predicate was found.")
}