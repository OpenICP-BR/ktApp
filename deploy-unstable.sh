#!/usr/bin/env bash

VERSION=`./get_version.sh`

GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

BINTRY_URL=https://api.bintray.com/content/gjvnq/misc/OpenICP-BR/${VERSION}

if [ "${BINTRAY_PASSWORD}" == "" ]; then
    echo -e "${RED}You MUST set the environment variable: ${BINTRAY_PASSWORD}${NC}"
    exit -1
fi

upload() {
    FILENAME=$1
    SRC=$2
    DST=$3
    echo curl -v -T ${SRC}/${FILENAME} -ugjvnq:\<TOKEN\> ${BINTRY_URL}/${DST}/${FILENAME}?publish=1
    curl -T ${SRC}/${FILENAME} -ugjvnq:${BINTRAY_PASSWORD} ${BINTRY_URL}/${DST}/${FILENAME}?publish=1
    echo
    echo -e "${GREEN}Deployed ${DST}/${FILENAME}${NC}"

}

echo -e "${GREEN}Deploying version: ${BLUE}${VERSION}${NC}..."

# Deploy JARs
cp "target/ktApp-${VERSION}.jar" "target/ktApp.jar"
upload "ktApp.jar" "target" ""
LIBS=`find target/lib -type f -iname "*.jar"`
for LIB in $LIBS; do
    LIB=`basename ${LIB}`
    upload "${LIB}" "target/lib" "/lib"
done
