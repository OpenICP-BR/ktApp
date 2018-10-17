#!/usr/bin/env bash

VERSION=`./get_version.sh`

GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

BRANCH=`git rev-parse --abbrev-ref HEAD`
BINTRY_URL=https://api.bintray.com/content/gjvnq/misc/OpenICP-BR.unstable/${BRANCH}

if [ "${BINTRAY_PASSWORD}" == "" ]; then
    echo -e "${RED}You MUST set the environment variable: ${BINTRAY_PASSWORD}${NC}"
    exit -1
fi

fail () {
    echo -e "${RED}Got error code $? from previous command.${NC}"
    exit -1
}

upload() {
    FILENAME=$1
    SRC=$2
    DST=$3
    ARGS="-o - -w \\n%{http_code}\\n ${BINTRY_URL}/${DST}/${FILENAME}?publish=1&override=0"
    echo curl -T ./${SRC}/${FILENAME} $ARGS
    curl -T ./${SRC}/${FILENAME} -ugjvnq:${BINTRAY_PASSWORD} ${ARGS} | tee curl.log
    cat curl.log | tail -n 1 | grep -e "2[0-9]\{2\}" > /dev/null || fail
    echo -e "${GREEN}Deployed ${DST}/${FILENAME}${NC}"
    rm curl.log
}

echo -e "${GREEN}Deploying version: ${BLUE}${VERSION}${NC}..."

# Zip all
LIBS=`find target/lib -type f -iname "*.jar"`
cp target/OpenICP-BR-unstable-${VERSION}.jar target/OpenICP-BR-unstable.jar
ZIP=target/OpenICP-BR-unstable-${VERSION}.zip
rm $ZIP
zip ${ZIP} target/ktApp.jar $LIBS
echo -e "${GREEN}Generated zip file: ${BLUE}${ZIP}${NC}"

# Upload file
upload $(basename $ZIP) target
