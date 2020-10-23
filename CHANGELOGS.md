# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

<!--
Types of changes
- Added for new features.
- Changed for changes in existing functionality.
- Deprecated for soon-to-be removed features.
- Removed for now removed features.
- Fixed for any bug fixes.
- Security in case of vulnerabilities.
-->

## [unreleased]

### Added

- POST `statuses/destroy` ([#20](https://github.com/yshrsmz/twitter4kt/issues/20))
- GET `statuses/home_timeline` ([#24](https://github.com/yshrsmz/twitter4kt/pull/24))
- GET `statuses/user_timeline` ([#24](https://github.com/yshrsmz/twitter4kt/pull/24))
- GET `favorites/list` ([#32](https://github.com/yshrsmz/twitter4kt/pull/32))
- `ApiResult.isSuccess()` and `ApiResult.isFailure()` to use smart cast.

### Changed

- `tweet_mode=extended` is now forced


## [0.1.4] - 2020-10-10

### Added

- Added more utility functions to `ApiResult`

### Changed

- `ApiResult` is now a part of `core-api` artifact


## [0.1.3] - 2020-10-10

### Changed

- Updated to kotlinx.serialization 1.0.0


## [0.1.2] - 2020-10-06

### Fixed

- No jar published


## [0.1.1] - 2020-10-04

### Added

- `ApiResult.fold(onSuccess, onFailure)` extension function
- Explicit API Mode is enabled

### Changed 

- `ApiResult` class is now a part of `core-model` artifact
- Functions in `OAuth1aFlow` now return `ApiResult`

### Fixed

- Error handling in oauth1a flow ([#15](https://github.com/yshrsmz/twitter4kt/issues/15))
- Remove `ktor-client-logging` dependency.


## [0.1.0] - 2020-10-04

initial release!

### Added

- POST `oauth/request_token` ([#1](https://github.com/yshrsmz/twitter4kt/issues/1))
- POST `oauth/access_token` ([#2](https://github.com/yshrsmz/twitter4kt/issues/2))
- GET `oauth/authenticate` ([#3](https://github.com/yshrsmz/twitter4kt/issues/3))
- GET `oauth/authorize` ([#4](https://github.com/yshrsmz/twitter4kt/issues/4))
- GET `account/verify_credentials` ([#5](https://github.com/yshrsmz/twitter4kt/issues/5))
- POST `statuses/update` ([#6](https://github.com/yshrsmz/twitter4kt/issues/6))
- POST `friendships/create` ([#11](https://github.com/yshrsmz/twitter4kt/issues/11))
