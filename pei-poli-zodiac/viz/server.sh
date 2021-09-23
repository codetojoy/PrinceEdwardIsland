#!/bin/bash

set -e

cp ../src/json_generator/zodiac.json . 

python3 -m http.server
