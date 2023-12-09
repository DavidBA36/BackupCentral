package com.david.backupcentral.helpers

import android.accounts.AccountManager
import android.content.Context
import android.os.Bundle
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.util.concurrent.Executor


interface HttpClientCallback<String> {
    fun onComplete(result:String)
    fun onError(result:String)
    fun onConnectionFailed(result:String)
    fun onStatus(result:String)
}

class HttpClient(executor: Executor, context: Context) {
    private var executor = executor
    private lateinit var httpConn: HttpURLConnection
    val am: AccountManager = AccountManager.get(context)
    val options = Bundle()

    fun Get(URL:String, callback: HttpClientCallback<String>) {
        this.executor.execute {
            try {
                val url = URL(URL)
                httpConn = url.openConnection() as HttpURLConnection
                httpConn.useCaches = false
                httpConn.requestMethod = "GET"
                httpConn.doInput = true
                httpConn.doOutput = false;

                if (httpConn != null) {
                    val status = httpConn.responseCode
                    val inputStream: InputStream
                    inputStream = if (status >= 400 && status <= 599) {
                        httpConn.errorStream
                    } else {
                        httpConn.inputStream
                    }
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val total = java.lang.StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        total.append(line).append('\n')
                    }
                    reader.close()
                    callback.onComplete(total.toString())
                } else {
                    callback.onConnectionFailed("")
                }

            } catch (e: IOException) {
                callback.onComplete(e.message.toString())
            }finally {
                if (httpConn != null) {
                    httpConn.disconnect();

                    callback.onStatus("Disconnected")
                }
            }
        }
    }

    fun Post(URL:String, params:Map<String, String> , callback: HttpClientCallback<String>) {
        this.executor.execute {
            try {
                val url = URL(URL)
                httpConn = url.openConnection() as HttpURLConnection
                httpConn.useCaches = false
                httpConn.requestMethod = "POST"
                httpConn.doInput = true
                httpConn.readTimeout = 60000;
                httpConn.connectTimeout = 5000;
                val requestParams = StringBuilder()
                if (params != null && params.isNotEmpty()) {
                    httpConn.doOutput = true;
                    for (entry in params.entries.iterator()) {
                        requestParams.append(URLEncoder.encode(entry.key, "UTF-8"))
                        requestParams.append("=").append(URLEncoder.encode(entry.value, "UTF-8"))
                        requestParams.append("&")
                    }
                    if (requestParams.isNotEmpty()) {
                        requestParams.setLength(requestParams.length - 1);
                    }
                    val writer = OutputStreamWriter(httpConn.outputStream)
                    writer.write(requestParams.toString())
                    writer.flush()
                }
                else {
                    callback.onError("params in POST request not given")
                }

                if (httpConn != null) {
                    val status = httpConn.responseCode
                    val inputStream: InputStream = if (status in 400..599) {
                        httpConn.errorStream
                    } else {
                        httpConn.inputStream
                    }
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val total = java.lang.StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        total.append(line).append('\n')
                    }
                    reader.close()
                    callback.onComplete(total.toString())
                } else {
                    callback.onConnectionFailed("")
                }
            } catch (e: IOException) {
                callback.onComplete(e.message.toString())
            }finally {
                if (httpConn != null) {
                    httpConn.disconnect();

                    callback.onStatus("Disconnected")
                }
            }
        }

    }
}