#!/bin/bash

set -e

cp ../zodiac*.json . 

python3 -m http.server
