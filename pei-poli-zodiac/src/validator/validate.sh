#!/bin/bash

set -e

SRC_DIR=../..


stat $SRC_DIR/zodiac.json > /dev/null 2>&1
stat $SRC_DIR/zodiac_elements.json > /dev/null 2>&1

echo "running..."
groovy Validator.groovy $SRC_DIR

echo "Ready."
