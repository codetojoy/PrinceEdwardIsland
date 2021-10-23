#!/bin/bash

set -e

cp ../zodiac.json . 
cp ../zodiac_elements.json . 

cp ../zodiac_fr.json . 
cp ../zodiac_elements_fr.json . 

python3 -m http.server
