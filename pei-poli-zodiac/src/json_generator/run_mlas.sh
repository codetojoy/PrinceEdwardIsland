#!/bin/bash

set -e

SRC_CSV=../../zodiac.csv
TARGET_ZODIAC=../../zodiac.json
TARGET_ELEMENTS=../../zodiac_elements.json

TARGET_ZODIAC_FR=../../zodiac_fr.json
TARGET_ELEMENTS_FR=../../zodiac_elements_fr.json

stat $SRC_CSV > /dev/null 2>&1

./gradlew clean test installDist
stat staging/bin/json_generator > /dev/null 2>&1

./staging/bin/json_generator normal $SRC_CSV $TARGET_ZODIAC en
./staging/bin/json_generator elements $SRC_CSV $TARGET_ELEMENTS en

./staging/bin/json_generator normal $SRC_CSV $TARGET_ZODIAC_FR fr
./staging/bin/json_generator elements $SRC_CSV $TARGET_ELEMENTS_FR fr

echo "Ready."
