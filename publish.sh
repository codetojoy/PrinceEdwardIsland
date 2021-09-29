#!/bin/bash

set -e 

./refresh-zodiac.sh
groovy VersionInfo.groovy 

cd ./web/pei-poli-zodiac
mv tmp.index.html index.html 
cd ../..

git add -u . 
git commit -m "incremental refresh"
git push origin gh-pages

echo "Ready."
