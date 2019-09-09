package com.example.loginoauth.api.auth

import android.content.Context
import br.com.mirabilis.oauth2authentication.api.APISettings
import com.example.loginoauth.api.base.APICreator
import com.example.loginoauth.model.oauth.request.Refresh
import com.example.loginoauth.model.oauth.Token
import com.example.loginoauth.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


object RefreshFetcher {

    class RefreshFetcherImpl(private val context: Context,
                          private val listener: Listener
    ){

        private var callback: Call<Token>? = null

        fun refresh(refresh: Refresh) {
            val authFetcher = APICreator(AuthAPI::class.java, APISettings.base).generate()
            callback = authFetcher.refresh(refresh)
            callback?.enqueue(object : Callback<Token> {

                override fun onResponse(call: Call<Token>?, response: Response<Token>?) {
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

                override fun onFailure(call: Call<Token>?, t: Throwable?) {
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
        fun onSuccess(token: Token?)
        fun onError(throwable: Throwable)
    }
}