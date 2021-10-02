#!/bin/bash

set -e

TARGET_CSV=../../zodiac.csv

groovy MyRandom.groovy > $TARGET_CSV 

echo "Ready."
