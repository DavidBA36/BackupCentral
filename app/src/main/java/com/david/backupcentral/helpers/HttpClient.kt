package com.david.backupcentral.helpers

import android.os.Handler
import com.david.backupcentral.models.ModelItem
import java.util.concurrent.Executor





interface HttpClientCallback<String> {
    fun onComplete(result:String)
}

class HttpClient {
    private lateinit var resultHandler: Handler
    private lateinit var executor: Executor

    fun HttpClient(executor: Executor, resultHandler: Handler) {
        this.executor = executor
        this.resultHandler = resultHandler
    }


    fun Post(jsonBody: String ,callback: HttpClientCallback<String>) {
        executor.execute(Runnable {
            try {
                callback.onComplete("hola")
            } catch (e: Exception) {
                callback.onComplete("hola")
            }
        })
    }



    /* private class HttpSendMessageThread internal constructor(
        private val RequestMethod: String,
        private val requestURL: String,
        private val params: Map<String, String>?
    ) :
        Thread() {
        override fun run() {
            try {
                val url = URL(requestURL)
                var httpConn = url.openConnection() as HttpURLConnection
                httpConn.setUseCaches(false)
                httpConn.setRequestMethod(RequestMethod)
                httpConn.setDoInput(true) // true if we want to read server's response
                if (RequestMethod == "GET") {
                    httpConn.setDoOutput(false) // false indicates this is a GET request
                } else {
                    httpConn.setReadTimeout(60000)
                    httpConn.setConnectTimeout(5000)
                    val requestParams = StringBuilder()
                    if (params != null && params.size > 0) {
                        httpConn.setDoOutput(true) // true indicates POST request
                        // creates the params string, encode them using URLEncoder
                        for (key in params.keys) {
                            val value = params[key]
                            requestParams.append(URLEncoder.encode(key, "UTF-8"))
                            requestParams.append("=").append(URLEncoder.encode(value, "UTF-8"))
                            requestParams.append("&")
                        }
                        if (requestParams.length > 0) {
                            requestParams.setLength(requestParams.length - 1)
                        }
                        // sends POST data
                        val writer = OutputStreamWriter(httpConn.getOutputStream())
                        writer.write(requestParams.toString())
                        writer.flush()
                    } else {
                        val result = Bundle()
                        result.putString("name", "details")
                        result.putString("value", "params in POST request not given")
                        HttpfireEvent(HttpEvent(HttpEventType.MALFORMED_REQUEST, null))
                    }
                }
                if (httpConn != null) {
                    val status: Int = httpConn.getResponseCode()
                    val inputStream: InputStream
                    inputStream = if (status >= 400 && status <= 599) {
                        httpConn.getErrorStream()
                    } else {
                        httpConn.getInputStream()
                    }
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val total = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        total.append(line).append('\n')
                    }
                    reader.close()
                    val result = Bundle()
                    result.putString("name", "message")
                    result.putString("value", total.toString())
                    HttpfireEvent(HttpEvent(HttpEventType.MESSAGE_RECEIVED, result))
                } else {
                    HttpfireEvent(HttpEvent(HttpEventType.CONNECTION_FAILED, null))
                }
            } catch (e: IOException) {
                val result = Bundle()
                result.putString("name", "exception")
                result.putString("value", e.message)
                HttpfireEvent(HttpEvent(HttpEventType.PROTOCOL_ERROR, result))
            } finally {
                if (httpConn != null) {
                    httpConn.disconnect()
                    HttpfireEvent(HttpEvent(HttpEventType.DISCONNECTED, null))
                }
            }
        }
    }

    override fun run() {
        TODO("Not yet implemented")
    }*/

}