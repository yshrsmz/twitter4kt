#!/usr/bin/env sh

./gradlew clean --no-daemon --no-parallel && ./gradlew uploadArchives --no-daemon --no-parallel
