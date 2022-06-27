package com.midterm.english_app.business.provider

object UrlProvider{
    class Yandex {
        companion object {
            val baseUrl = "https://translate.yandex.net"
            val format = "plain"
            val lang = "en-vi"
            val key = APIKey.Yandex.key
        }
    }
}