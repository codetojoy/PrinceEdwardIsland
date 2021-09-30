#!/bin/bash

set -e

cp ../zodiac.json . 
cp ../zodiac_elements.json . 

python3 -m http.server
