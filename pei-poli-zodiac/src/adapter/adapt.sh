#!/bin/bash

set -e

SRC_DIR=../..

stat $SRC_DIR/zodiac_premiers.csv > /dev/null 2>&1

echo "running..."
groovy Adapter.groovy $SRC_DIR

echo "Ready."
