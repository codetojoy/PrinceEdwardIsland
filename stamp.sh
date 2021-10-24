#!/bin/bash

set -e 

./refresh-zodiac.sh
groovy VersionInfo.groovy 

cd ./web/pei-poli-zodiac
mv tmp.info.html info.html 
cd ../..

echo "Ready."
