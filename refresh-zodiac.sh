#!/bin/bash

set -e 

SRC_DIR=../../PrinceEdwardIsland/pei-poli-zodiac
DEST_DIR=./web/pei-poli-zodiac

# --------- assert 

stat $SRC_DIR/zodiac.json > /dev/null 2>&1
stat $SRC_DIR/zodiac_elements.json > /dev/null 2>&1

stat $SRC_DIR/zodiac_fr.json > /dev/null 2>&1
stat $SRC_DIR/zodiac_elements_fr.json > /dev/null 2>&1

stat $SRC_DIR/viz/index.html > /dev/null 2>&1
stat $SRC_DIR/viz/index_fr.html > /dev/null 2>&1

stat $SRC_DIR/viz/zodiac.css > /dev/null 2>&1

stat $SRC_DIR/viz/app.js > /dev/null 2>&1
stat $SRC_DIR/viz/const.js > /dev/null 2>&1

stat $SRC_DIR/viz/info.html > /dev/null 2>&1
stat $SRC_DIR/viz/info_fr.html > /dev/null 2>&1
stat $SRC_DIR/viz/info.css > /dev/null 2>&1

stat $SRC_DIR/viz/other.html > /dev/null 2>&1
stat $SRC_DIR/viz/other_fr.html > /dev/null 2>&1

stat $SRC_DIR/viz/nav.css > /dev/null 2>&1
stat $SRC_DIR/viz/nav.js > /dev/null 2>&1

# --------- copy 

cp $SRC_DIR/zodiac.json $DEST_DIR/.
cp $SRC_DIR/zodiac_elements.json $DEST_DIR/.

cp $SRC_DIR/zodiac_fr.json $DEST_DIR/.
cp $SRC_DIR/zodiac_elements_fr.json $DEST_DIR/.

cp $SRC_DIR/viz/index.html $DEST_DIR/.
cp $SRC_DIR/viz/index_fr.html $DEST_DIR/.

cp $SRC_DIR/viz/zodiac.css $DEST_DIR/.

cp $SRC_DIR/viz/app.js $DEST_DIR/.
cp $SRC_DIR/viz/const.js $DEST_DIR/.

cp $SRC_DIR/viz/info.html $DEST_DIR/.
cp $SRC_DIR/viz/info_fr.html $DEST_DIR/.
cp $SRC_DIR/viz/info.css $DEST_DIR/.

cp $SRC_DIR/viz/other.html $DEST_DIR/.
cp $SRC_DIR/viz/other_fr.html $DEST_DIR/.

cp $SRC_DIR/viz/nav.css $DEST_DIR/.
cp $SRC_DIR/viz/nav.js $DEST_DIR/.
