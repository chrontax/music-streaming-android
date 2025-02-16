package org.chrontax.musicstreaming.network

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import org.chrontax.musicstreaming.data.SettingsManager
import java.util.Date
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val settingsManager: SettingsManager) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { settingsManager.tokenFlow.first() }
        if (token != null && isTokenExpired(token)) {
            runBlocking { settingsManager.clearToken() }

            return Response.Builder()
                .code(401)
                .message("Unauthorized")
                .request(chain.request())
                .protocol(chain.connection()?.protocol()!!)
                .body("Token expired".toResponseBody("text/plain".toMediaType()))
                .build()
        }
        val request = chain.request().newBuilder()
        token?.let {
            request.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(request.build())
    }
}

fun isTokenExpired(token: String): Boolean {
    return try {
        val claims: Claims = Jwts.parser().build().parseUnsecuredClaims(token).payload;
        val expirationDate = claims.expiration
        expirationDate.before(Date())
    } catch (e: Exception) {
        true
    }
}