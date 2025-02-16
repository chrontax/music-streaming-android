package org.chrontax.musicstreaming.data

sealed class UserApiResult {
    data class Success(val token: String) : UserApiResult()
    data class Failure(val message: String) : UserApiResult()
}

fun String.success(): UserApiResult = UserApiResult.Success(this)