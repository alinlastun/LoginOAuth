package com.example.loginoauth.model.oauth.request

import java.io.Serializable


data class Auth(val email: String,
                val password: String) : Serializable
