package com.gabo.quiz5.helpers

sealed class ResultHandler<T> {
    data class Success<T>(val list: T) : ResultHandler<T>()
    data class Error<T>(val errorMSg: String) : ResultHandler<T>()
}