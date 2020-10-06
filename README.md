twitter4kt
===

Twitter API client for Kotlin Multiplatform Project.

Currently, only available for jvm.

## Setup

### For build.gradle.kts

LATEST_VERSION: `0.1.2`

```kotlin
plugins {
  id("org.jetbrains.kotlin.multiplatform") version "1.4.10"
}

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

            // if you need logging for HTTP calls
            implementatin("io.ktor:ktor-client-logging:$KTOR_VERSION")
        }
    }
}
```


## Usage

```kotlin
val twitter = Twitter {
    consumerKeys = ConsumerKeys(
        key = "",
        secret = ""
    )
    httpClientConfig = {
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.ALL
        }
    }
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

val result: ApiResult<Tweet> = client.statuses.update(status = "Hello from twitter4kt!")
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
```

We use these values for testing
