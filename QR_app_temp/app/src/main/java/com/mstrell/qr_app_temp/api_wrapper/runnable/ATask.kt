package com.mstrell.qr_app_temp.api_wrapper.runnable

import android.os.AsyncTask
import com.google.gson.Gson
import com.mstrell.qr_app_temp.api_wrapper.models.*
import okhttp3.*


class ATask(val method: String, val url: String,val args: HashMap<String, String>,val jwt: Boolean)
    : AsyncTask<Int, String, Any>() {
    override fun doInBackground(vararg params: Int?): Any? {
        var jResponse: String?
        try {
            if (method == "POST") jResponse = postReq(url, args, jwt)
            else if (method == "GET") jResponse = getReq(url, args, jwt)
            else throw IllegalArgumentException()
            return when (url) {
                Urls.url_org_data -> Gson().fromJson(jResponse, Organization::class.java)
                Urls.url_emp_tokens -> Gson().fromJson(jResponse, JWT::class.java)
                Urls.url_refresh -> Gson().fromJson(jResponse, JWT::class.java)
                Urls.url_end -> Gson().fromJson(jResponse, JWT::class.java)
                Urls.url_emp_data -> Gson().fromJson(jResponse, Employee::class.java)
                Urls.url_tickets -> Gson().fromJson(jResponse, Tickets::class.java)
                Urls.url_ticket_data -> Gson().fromJson(jResponse, Ticket::class.java)
                Urls.url_repay -> Gson().fromJson(jResponse, RepayStatus::class.java)
                else -> null
            }

        } catch (e: Exception) {return null}
    }

    fun postReq(url: String, params: HashMap<String, String>, jwt: Boolean): String? {
        val body: RequestBody = RequestBody.create(JSON, params["body"]!!);
        val request = Request.Builder()
            .url(url)
            .post(body)
            .also { if (jwt) it.addHeader("authorization", "Bearer accessToken") }
            .build()

        return client.newCall(request).execute()?.message()
    }


    fun getReq(url: String, params: HashMap<String, String>?, jwt: Boolean): String? {
        val httpBuilder = HttpUrl.parse(url)!!.newBuilder()
        if (params != null) {
            for ((key, value) in params.entries) {
                httpBuilder.addQueryParameter(key, value)
            }
        }
        val request = Request.Builder()
            .url(httpBuilder.build())
            .also { if (jwt) it.addHeader("authorization", "Bearer accessToken") }
            .build()


        return client.newCall(request).execute()?.message()
    }

    companion object {
        val client = OkHttpClient()
        val JSON: MediaType? = MediaType.parse("application/json; charset=utf-8");
    }

}