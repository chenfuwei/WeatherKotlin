package com.custom.weather.domain.commands

interface Command<out T> {
    fun execute() : T
}