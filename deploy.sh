#!/usr/bin/env bash

VERSION=`./get_version.sh`

GREEN='\033[0;32m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${GREEN}Deploying version: ${BLUE}${VERSION}${NC}..."

# Deploy jar
echo mvn deploy -DskipTests
mvn deploy -DskipTests
echo
echo -e "${GREEN}Deployed to maven repo${NC}"

# Generate .deb file
echo curl -v -T target/openicp-br-${VERSION}.deb -ugjvnq:\<TOKEN\>\
    https://api.bintray.com/content/gjvnq/deb/openicp-br/${VERSION}/o/openicp/openicp-br-${VERSION}.deb
curl -T target/openicp-br-${VERSION}.deb -ugjvnq:${BINTRAY_PASSWORD}\
    https://api.bintray.com/content/gjvnq/deb/openicp-br/${VERSION}/o/openicp/openicp-br-${VERSION}.deb
echo
echo -e "${GREEN}Deployed .deb package${NC}"