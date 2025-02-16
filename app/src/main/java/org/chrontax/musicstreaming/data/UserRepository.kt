package org.chrontax.musicstreaming.data

import org.chrontax.musicstreaming.network.UserApi
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepository @Inject constructor(private val userApi: UserApi) {
    suspend fun login(user: User): UserApiResult {
        return try {
            val response = userApi.login(user)
            if (response.isSuccessful) {
                response.body()?.string()?.success() ?: UserApiResult.Failure("No token received")
            } else {
                UserApiResult.Failure("Invalid credentials")
            }
        } catch (e: HttpException) {
            UserApiResult.Failure("HTTP error: ${e.message}")
        } catch (e: IOException) {
            UserApiResult.Failure("Network error: ${e.message}")
        } catch (e: Exception) {
            UserApiResult.Failure("Unknown error: ${e.message}")
        }
    }

    suspend fun register(user: User): UserApiResult {
        return try {
            val response = userApi.register(user)
            if (response.isSuccessful) {
                response.body()?.string()?.success() ?: UserApiResult.Failure("no token received")
            } else {
                UserApiResult.Failure(response.message())
            }
        } catch (e: HttpException) {
            UserApiResult.Failure("HTTP error: ${e.message}")
        } catch (e: IOException) {
            UserApiResult.Failure("Network error: ${e.message}")
        } catch (e: Exception) {
            UserApiResult.Failure("Unknown error: ${e.message}")
        }
    }
}