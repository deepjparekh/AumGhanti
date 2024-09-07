package org.deepparekh.aumghanti

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform