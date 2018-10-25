#!/usr/bin/env bash

./fix_version.sh
VERSION=`./get_version.sh`

GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

DEB=openicp-br-${VERSION}.deb
BINTRY_URL=https://api.bintray.com/content/gjvnq/misc/OpenICP-BR.unstable/all

if [ "${BINTRAY_PASSWORD}" == "" ]; then
    echo -e "${RED}You MUST set the environment variable: BINTRAY_PASSWORD${NC}"
    exit -1
fi
if [ ! -f "${DEB}" ]; then
    echo -e "${RED}File ${BLUE}${DEB}${RED} does not exist.\n${NC}Please run: mvn clean package${NC}"
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
    #curl -T ./${SRC}/${FILENAME} -ugjvnq:${BINTRAY_PASSWORD} ${ARGS} | tee curl.log
    cat curl.log | tail -n 1 | grep -e "2[0-9]\{2\}" > /dev/null || fail
    echo -e "${GREEN}Deployed ${DST}/${FILENAME}${NC}"
    rm curl.log
}

echo -e "${GREEN}Deploying version: ${BLUE}${VERSION}${NC}..."

# Deploy jar
echo mvn deploy -DskipTests
mvn deploy -DskipTests
echo
echo -e "${GREEN}Deployed to maven repo${NC}"

# Send .deb file
upload ${DEB} target o/openicp/
echo curl -v -T target/openicp-br-${VERSION}.deb -ugjvnq:\<TOKEN\>\
    https://api.bintray.com/content/gjvnq/deb/openicp-br/${VERSION}/o/openicp/openicp-br-${VERSION}.deb?publish=1&override=1
#curl -T target/openicp-br-${VERSION}.deb -ugjvnq:${BINTRAY_PASSWORD}\
    https://api.bintray.com/content/gjvnq/deb/openicp-br/${VERSION}/o/openicp/openicp-br-${VERSION}.deb?publish=1&override=1
echo
echo -e "${GREEN}Deployed .deb package${NC}"