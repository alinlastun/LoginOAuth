package com.example.loginoauth.api.auth

import android.content.Context
import android.media.session.MediaSession
import br.com.mirabilis.oauth2authentication.api.APISettings
import com.example.loginoauth.api.base.APICreator
import com.example.loginoauth.model.oauth.request.Auth
import com.example.loginoauth.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object AuthFetcher {

    class AuthFetcherImpl(private val context: Context,
                          private val listener: Listener
    ){

        private var callback: Call<MediaSession.Token>? = null

        fun auth(auth: Auth) {
            val authFetcher = APICreator(AuthAPI::class.java, APISettings.base).generate()
            callback = authFetcher.auth(auth)
            callback?.enqueue(object : Callback<MediaSession.Token> {

                override fun onResponse(call: Call<MediaSession.Token>?, response: Response<MediaSession.Token>?) {
                    if(response != null){
                        if(response.isSuccessful){
                            listener.onSuccess(response.body())
                        }else{
                            listener.onSuccess(null)
                        }
                    }else{
                        listener.onError(Throwable(context.getString(R.string.auth_error)))
                    }
                }

                override fun onFailure(call: Call<MediaSession.Token>?, t: Throwable?) {
                    val msg = context.getString(R.string.auth_error)
                    listener.onError(Throwable("$msg : ${t?.message}"))
                }
            })
        }

        fun cancel(){
            callback?.cancel()
        }
    }

    interface Listener {
        fun onSuccess(token: MediaSession.Token?)
        fun onError(throwable: Throwable)
    }
}