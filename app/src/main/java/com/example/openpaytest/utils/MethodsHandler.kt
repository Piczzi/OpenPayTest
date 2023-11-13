package com.example.openpaytest.utils

object MethodsHandler {

    fun validateEmptyField(value: String?): String {
        return if (value.isNullOrBlank()) Constants.EMPTY_LABEL else value
    }

}