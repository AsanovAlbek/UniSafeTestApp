package albek.petprojects.unisafetestapp.core.model

import java.lang.reflect.InvocationTargetException
import java.net.ConnectException
import java.net.ProtocolException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun Throwable.isUnsuccessfulResponse() = this is UnsuccessfulResponseException
fun Throwable.isNetworkException() = when(this) {
    is ConnectException,
    is SocketException,
    is SocketTimeoutException,
    is UnknownHostException,
    is ProtocolException -> true

    else -> false
}