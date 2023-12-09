package com.david.backupcentral.helpers


abstract class HttpEvent<T> {
    private fun HttpEvent() {}


    class Success<T>(var data: T) : HttpEvent<T>()


    class Error<T>(var exception: Exception) : HttpEvent<T>()


}