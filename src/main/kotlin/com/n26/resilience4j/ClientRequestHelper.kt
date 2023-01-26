package com.n26.resilience4j

import org.slf4j.LoggerFactory
import retrofit2.Call
import retrofit2.Response

private val log = LoggerFactory.getLogger("pet.project")

typealias HttpCodeProcessor<T> = Pair<Int, () -> T?>

fun <T> Call<out T>.getResponse(): T =
    executeAndHandleResponse() ?: throw RuntimeException("Unable to get response body")

fun <T> Call<out T>.executeAndHandleResponse(vararg httpCodeProcessors: HttpCodeProcessor<T>): T? = try {
    log.info("process=httpCall httpMethod=${this.request().method} url='${this.request().url.host}'")
    this.clone().execute().let { resp ->
        return getResponseBody<T>(httpCodeProcessors, resp)
    }
} catch (e: Exception) {
    throw if (e is RuntimeException) e else RuntimeException(e)
}

private fun <T> getResponseBody(
    httpCodeProcessors: Array<out HttpCodeProcessor<T>>,
    resp: Response<out T>
): T? = when {
    httpCodeProcessors.toMap()
        .containsKey(resp.code()) -> log.info("Executing code processor for response: $resp")
        .let { httpCodeProcessors.toMap()[resp.code()]?.invoke() }

    resp.isSuccessful ->
        resp.body()
            .also { log.info("process=httpCall status=success httpCode=${resp.code()}") }

    resp.isClientError() ->
        throw RuntimeException(
            "Failed to execute http call due to client error, response='${resp.errorBody()?.string()}'"
        ).also { logFailure(resp) }

    else ->
        throw ResilienceProcessedException(
            resp.code(),
            "Failed to execute http call due to server error: response='${resp.errorBody()?.string()}'"
        ).also { logFailure(resp) }
}

private fun <T> logFailure(resp: Response<out T>) {
    log.error("process=httpCall status=failed httpCode=${resp.code()} responseBody='${resp.errorBody()?.string()}'")
}

fun <T> Response<T>.isClientError() = this.code() in 400..499
