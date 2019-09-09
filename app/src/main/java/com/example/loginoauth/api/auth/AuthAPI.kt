package com.example.loginoauth.api.auth


import com.example.loginoauth.model.oauth.Token
import com.example.loginoauth.model.oauth.request.Auth
import com.example.loginoauth.model.oauth.request.Refresh
import retrofit2.http.Body
import retrofit2.http.POST


interface AuthAPI {

    /**
     * Your endpoint of auth
     */
    @POST("auth")
    fun auth(@Body auth: Auth): retrofit2.Call<Token>

    /**
     * Your endpoint of refresh your token
     */
    @POST("refresh")
    fun refresh(@Body refreshAuth: Refresh): retrofit2.Call<Token>
}