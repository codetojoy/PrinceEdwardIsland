#!/bin/bash

set -e

stat ../../zodiac.csv > /dev/null 2>&1
# cp ../../zodiac.csv .

./gradlew clean installDist
stat staging/bin/json_generator > /dev/null 2>&1

./staging/bin/json_generator ../../zodiac.csv ../../zodiac.json

cp ../../zodiac.json ../../viz/.

echo "Ready."
