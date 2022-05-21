#!/bin/bash

docker run \
    --rm \
    -u gradle \
    -v "$PWD":/home/gradle/project \
    -w /home/gradle/project \
    gradle:7.4.2-jdk8 \
    gradle wrapper
