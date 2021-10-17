#!/bin/bash

set -e 

./refresh-zodiac.sh
groovy VersionInfo.groovy 

cd ./web/pei-poli-zodiac
mv tmp.info.html info.html 
cd ../..

git add -u . 
git add web/pei-poli-zodiac
git commit -m "incremental refresh"
git push origin gh-pages

echo "Ready."
