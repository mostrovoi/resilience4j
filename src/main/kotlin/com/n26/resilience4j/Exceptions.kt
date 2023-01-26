package com.n26.resilience4j

class ResilienceProcessedException(val code: Int, message: String) : RuntimeException(message)