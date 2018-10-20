#!/usr/bin/env bash

./fix_version.sh
VERSION=`./get_version.sh`

GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

JAR=target/ktApp-${VERSION}.jar
BRANCH=`git rev-parse --abbrev-ref HEAD`
BINTRY_URL=https://api.bintray.com/content/gjvnq/misc/OpenICP-BR.unstable/${BRANCH}

if [ "${BINTRAY_PASSWORD}" == "" ]; then
    echo -e "${RED}You MUST set the environment variable: BINTRAY_PASSWORD${NC}"
    exit -1
fi
if [ ! -f "${JAR}" ]; then
    echo -e "${RED}File ${BLUE}${JAR}${RED} does not exist.\nPlease run: mvn clean package${NC}"
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
    ARGS="-o - -w \\n%{http_code}\\n ${BINTRY_URL}/${DST}/${FILENAME}?publish=1&override=1"
    echo curl -T ./${SRC}/${FILENAME} $ARGS
    curl -T ./${SRC}/${FILENAME} -ugjvnq:${BINTRAY_PASSWORD} ${ARGS} | tee curl.log
    cat curl.log | tail -n 1 | grep -e "2[0-9]\{2\}" > /dev/null || fail
    echo -e "${GREEN}Deployed ${DST}/${FILENAME}${NC}"
    rm curl.log
}

echo -e "${GREEN}Deploying version: ${BLUE}${VERSION}${NC}..."

# Zip all
cd target
LIBS=`find lib -type f -iname "*.jar"`
ZIP=OpenICP-BR-unstable-${VERSION}.zip
rm $ZIP
cp $(basename ${JAR}) ktApp.jar
zip ${ZIP} ktApp.jar ${LIBS}
echo -e "${GREEN}Generated zip file: ${BLUE}target/${ZIP}${NC}"

# Upload file
upload $ZIP
