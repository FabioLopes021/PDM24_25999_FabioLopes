package com.example.store.utils

import android.content.Context
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import com.example.store.domain.model.Produto

sealed class AuthState{
    object Authenticated: AuthState()
    object Unauthenticated: AuthState()
    //object loading: AuthState()
    data class Error(
        val message: String
    ): AuthState()
}

fun showToastError(
    context: Context,
    e: Exception
) = makeText(context, "${e.message}", LENGTH_LONG).show()

fun showToastMessage(
    context: Context,
    message: String
) = makeText(context, "${message}", LENGTH_LONG).show()

