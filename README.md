# twitter4kt

Twitter API client for Kotlin Multiplatform


```kotlin
val config = TwitterApiConfig(
    apiKey="",
    apiKeySecret=""
)
val twitter = Twitter4kt.initialize(config)

val authFlow = twitter.launchOAuthFlow()

launch(Dispatchers.io) {
    val requestToken: RequestToken = authFlow.fetchRequestToken()
 
    val url = requestToken.authorizationUrl
    val pinCode = ""

    val accessToken = authFlow.fetchAccessToken(requestToken, pinCode)

    authFlow.destroy()
}

val accessTokens = TwitterAccessToken(
    accessToken="",
    accessTokenSecret=""
)
```