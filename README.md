# twitter4kt

Twitter API client for Kotlin Multiplatform


```kotlin
val config = TwitterApiConfig(
    apiKey="",
    apiKeySecret=""
)
val twitter = Twitter4kt.initialize(config)

val twitter = Twitter {
 consumerKeys = ConsumerKeys(
  key = "",
  secret = ""
 )
}

val authFlow = twitter.launchOAuthFlow()

val accessToken = launch(Dispatchers.io) {
    val requestToken: RequestToken = authFlow.fetchRequestToken()
 
    val url = requestToken.authorizationUrl
    val pinCode = ""

    val accessToken = authFlow.fetchAccessToken(requestToken, pinCode)

    authFlow.destroy()
    accessToken
}

val client = twitter.startSession(accessToken)

val accessTokens = AccessToken(
    token="",
    secret=""
)
```
