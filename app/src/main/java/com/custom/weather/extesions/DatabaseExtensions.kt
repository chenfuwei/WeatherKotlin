package com.custom.weather.extesions

import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.SelectQueryBuilder

fun <T : Any> SelectQueryBuilder.parseList(parse:(Map<String, Any?>) -> T) : List<T> =
        parseList(object : MapRowParser<T>{
            override fun parseRow(columns: Map<String, Any?>): T = parse(columns)
        })

fun <T :Any> SelectQueryBuilder.parseOpt(parse: (Map<String, Any?>) -> T): T? =
        parseOpt(object : MapRowParser<T>{
            override fun parseRow(columns: Map<String, Any?>): T = parse(columns)
        })

fun SQLiteDatabase.clear(tableName: String)
{
    execSQL("delete from $tableName")
}

fun SelectQueryBuilder.byId(id : Long) = whereSimple("_id = ?", id.toString())