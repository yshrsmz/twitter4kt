twitter4kt
===

Twitter API client for Kotlin Multiplatform Project.

Currently, only available for jvm.

## Setup

### For build.gradle.kts

LATEST_VERSION: `0.1.0`

```kotlin
repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://dl.bintray.com/yshrsmz/twitter4kt")
}

kotlin {
    jvm()

    sourceSets {
        val commonMain by getting {
            implementation("com.codingfeline.twitter4kt:core-api:$LATEST_VERSION")
            implementation("com.codingfeline.twitter4kt:v1-api:$LATEST_VERSION")
        }
    }
}
```


## Usage

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


## Development

Create `./secrets.properties`

```properties
twitter_consumer_key=
twitter_consumer_secret=
twitter_access_token=
twitter_access_token_secret=
twitter_user_id=
twitter_screen_name=
#
# below values can be empty(I guess)
BINTRAY_USER=
BINTRAY_API_KEY=
```

We use these values for testing
