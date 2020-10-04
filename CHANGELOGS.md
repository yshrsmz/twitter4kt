# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## Unreleased

### Added

- `ApiResult.fold(onSuccess, onFailure)` extension function

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
