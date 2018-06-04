package com.custom.weather.extesions

import android.content.Context
import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

object DelegatesExt{
    fun <T> notNullSingleValue() = NotNullSingleValueVar<T>()
    fun <T> preference(context: Context, name: String, default: T) = Preference(context, name, default)
}

class NotNullSingleValueVar<T> : ReadWriteProperty<Any?, T>{
    private var value : T? = null
    override fun getValue(thisRef: Any?, property: KProperty<*>): T =
       value ?: throw IllegalStateException("${thisRef?.javaClass?.simpleName} ${property.name} not initialized")

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if(this.value == null)
        {
            this.value = value
        }else{
            throw IllegalStateException("${thisRef?.javaClass?.simpleName} ${property.name} aleady initialized")
        }
    }
}

class Preference<T>(private val context: Context, private val name: String, private val default: T?) : ReadWriteProperty<Any?, T>
{
    private val preference: SharedPreferences by lazy { context.getSharedPreferences("default", Context.MODE_PRIVATE) }
    override fun getValue(thisRef: Any?, property: KProperty<*>): T = findPreference(name, default)

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putPreference(name, value)
    }

    private fun findPreference(name: String,  default: T?) : T = with(preference){
            val res = when(default){
                is Int -> getInt(name, default)
                is Long -> getLong(name, default)
                is String -> getString(name, default)
                is Boolean -> getBoolean(name, default)
                is Float -> getFloat(name, default)
                else -> throw IllegalArgumentException("This type can be saved into Preferences")
            }
            res as T
        }

    private fun putPreference(name: String, value: T?){
        with(preference.edit()){
            when(value){
                is Int -> putInt(name, value)
                is Long -> putLong(name, value)
                is String -> putString(name, value)
                is Boolean -> putBoolean(name, value)
                is Float -> putFloat(name, value)
                else -> throw IllegalStateException("This type can't be saved into Preferences")
            }
        }.apply()
    }
}

