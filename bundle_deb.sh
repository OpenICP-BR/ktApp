#!/bin/bash

# DEPENDENCY: dpkg-deb

GREEN='\033[0;32m'
RED='\033[0;31m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

./fix_version.sh
VERSION=`./get_version.sh`
JAR=target/ktApp-${VERSION}.jar

fail () {
    echo -e "${RED}Got error code $? from previous command.${NC}"
    exit -1
}

if [ ! -f "${JAR}" ]; then
    echo -e "${RED}File ${BLUE}${JAR}${RED} does not exist.${NC}\nPlease run: mvn clean package${NC}"
    exit -1
fi

DEB_PATH=target/openicp-br-${VERSION}.deb

# Clear stuff and make structure
rm -vrf target/deb/
mkdir -pv target/deb/DEBIAN || fail
mkdir -pv target/deb/usr/bin || fail
mkdir -pv target/deb/usr/share/applications || fail
mkdir -pv target/deb/usr/share/openicp-br/lib || fail
for RES in scalable 16x16 32x32 48x48 64x64 128x128 256x256; do
    mkdir -pv target/deb/usr/share/icons/hicolor/${RES}/apps || fail
done
echo -e "${GREEN}Cleaned previous files and created structure ${NC}"

# Copy files
cp -v ${JAR} target/ktApp.jar || fail
cp -v other_res/linux/openicpbr target/deb/usr/bin/ || fail
chmod +x target/deb/usr/bin/openicpbr || fail
cp -v target/ktApp.jar target/deb/usr/share/openicp-br/ || fail
cp -v target/lib/*.jar target/deb/usr/share/openicp-br/lib/ || fail
cp -v other_res/linux/com.github.OpenICP_BR.ktApp.desktop  target/deb/usr/share/applications || fail
cp -v res/logo.svg target/deb/usr/share/icons/hicolor/scalable/apps/com.github.OpenICP_BR.ktApp.svg || fail
for RES in 16x16 32x32 48x48 64x64 128x128 256x256; do
    cp -v res/logo-${RES}.png target/deb/usr/share/icons/hicolor/${RES}/apps/com.github.OpenICP_BR.ktApp.png || fail
done
echo -e "${GREEN}Copied files ${NC}"

# Get total file size
SIZE=`du -s target/deb | cut -f1`

# Send config file
cat other_res/linux/deb/control | sed "s/{VERSION}/${VERSION}/g"  | sed "s/{SIZE}/${SIZE}/g" > target/deb/DEBIAN/control || fail
echo -e "${GREEN}Config written ${NC}"

dpkg-deb -v -b target/deb "${DEB_PATH}" || fail
echo -e "${GREEN}Generated .deb file ${NC}"