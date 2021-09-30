#!/bin/bash

set -e

stat ../../zodiac.csv > /dev/null 2>&1

./gradlew clean test installDist
stat staging/bin/json_generator > /dev/null 2>&1

./staging/bin/json_generator normal ../../zodiac.csv ../../zodiac.json
./staging/bin/json_generator elements ../../zodiac.csv ../../zodiac_elements.json

cp ../../zodiac.json ../../viz/.
cp ../../zodiac_elements.json ../../viz/.

echo "Ready."
