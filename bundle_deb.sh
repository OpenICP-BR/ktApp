#!/bin/bash

# DEPENDENCY: dpkg-deb

GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m' # No Color

VERSION=$1

fail () {
    echo -e "${RED}Got error code $? from previous command.${NC}"
    exit -1
}

if [[ -z "${VERSION}" ]]; then
    echo -e "${RED}You MUST specify the version number${NC}"
    exit -2
fi

DEB_PATH=target/openicp-br-${VERSION}.deb

# Clear stuff and make structure
rm -vrf target/deb/
mkdir -pv target/deb/DEBIAN || fail
mkdir -pv target/deb/usr/bin || fail
mkdir -pv target/deb/usr/share/applications || fail
mkdir -pv target/deb/usr/share/openicp-br/lib || fail
echo -e "${GREEN}Cleaned previous files and created structure ${NC}"

# Copy files
cp -v target/ktApp-${VERSION}.jar target/deb/usr/share/openicp-br/ || fail
cp -v target/lib/*.jar target/deb/usr/share/openicp-br/lib/ || fail
cp -v other_res/linux/com.github.OpenICP_BR.ktApp.desktop  target/deb/usr/share/applications || fail
echo -e "${GREEN}Copied files ${NC}"

# Get total file size
SIZE=`du -s target/deb | cut -f1`

# Send config file
cat other_res/linux/deb/control | sed "s/{VERSION}/${VERSION}/g"  | sed "s/{SIZE}/${SIZE}/g" > target/deb/DEBIAN/control || fail
echo -e "${GREEN}Config written ${NC}"

dpkg-deb -v -b target/deb "${DEB_PATH}" || fail
echo -e "${GREEN}Generated .deb file ${NC}"