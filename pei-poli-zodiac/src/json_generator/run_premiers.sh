#!/bin/bash

set -e

PREFIX=zodiac_premiers
SRC_CSV="../../${PREFIX}_normalized.csv"
TARGET_ZODIAC="../../${PREFIX}.json"
TARGET_ELEMENTS="../../${PREFIX}_elements.json"

TARGET_ZODIAC_FR="../../${PREFIX}_fr.json"
TARGET_ELEMENTS_FR="../../${PREFIX}_elements_fr.json"

echo "stat check: $SRC_CSV"
stat $SRC_CSV > /dev/null 2>&1
echo "stat check OK"

./gradlew clean test installDist
stat staging/bin/json_generator > /dev/null 2>&1

./staging/bin/json_generator normal $SRC_CSV $TARGET_ZODIAC en
./staging/bin/json_generator elements $SRC_CSV $TARGET_ELEMENTS en

./staging/bin/json_generator normal $SRC_CSV $TARGET_ZODIAC_FR fr
./staging/bin/json_generator elements $SRC_CSV $TARGET_ELEMENTS_FR fr

echo "Ready."
