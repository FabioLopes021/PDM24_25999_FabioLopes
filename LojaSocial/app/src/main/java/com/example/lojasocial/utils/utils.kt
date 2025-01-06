package com.example.lojasocial.utils

import android.content.Context
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import com.example.lojasocial.domain.model.User

sealed class AuthState{
    object Authenticated: AuthState()
    object Unauthenticated: AuthState()
    //object Loading: AuthState()
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

fun guardarDadosUtilizador(context: Context, user: User): Boolean{
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    editor.putString("userId", user.userId)
    editor.putString("email", user.email)
    editor.putString("name", user.name)
    editor.putString("surename", user.surename)
    editor.putString("picture", user.picture)
    editor.putInt("roleId", user.roleId)

    return editor.commit()
}


fun getDadosUtilizador(context: Context): User? {
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    val userId = sharedPreferences.getString("userId", null)
    val email = sharedPreferences.getString("email", null)
    val name = sharedPreferences.getString("name", null)
    val surename = sharedPreferences.getString("surename", null)
    val picture = sharedPreferences.getString("picture", null)
    val roleId = sharedPreferences.getInt("roleId", -1)

    return if (userId != null && email != null && name != null && surename != null && picture != null && roleId != -1) {
        User(userId, email, name, surename, picture, roleId)
    } else {
        null
    }
}

fun clearUserData(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    return sharedPreferences.edit().clear().commit()
}