#!/bin/bash

# DEPENDENCY: https://github.com/sindresorhus/create-dmg
# DEPENDENCY: https://github.com/Jorl17/jar2app

# BUG: version is not being set
# BUG: adding a JRE does not work

GREEN='\033[0;32m'
BLUE='\033[0;34m'
RED='\033[0;31m'
NC='\033[0m' # No Color

VERSION=`./get_version.sh`
JAR=target/ktApp-${VERSION}.jar
JDK=/Library/Java/JavaVirtualMachines/jdk-10.jdk/Contents

fail () {
    echo -e "${RED}Got error code $? from previous command.${NC}"
    exit -1
}

if [ ! -f "${JAR}" ]; then
    echo -e "${RED}File ${BLUE}${JAR}${RED} does not exist.\nPlease run: mvn clean package${NC}"
    exit -1
fi

if [[ "$1" ]]; then
    JDK=$1
fi
echo -e "Using JDK on: ${JDK}"

# Get on the correct directory
cd target

# Prepare JRE for bundling
rm -vrf jdk-10.jdk
mkdir -p jdk-10.jdk/Contents/Home/jre/ || fail
ln -s ${JDK}/Contents/MacOS jdk-10.jdk/Contents || fail
ln -s ${JDK}/Contents/Home/bin jdk-10.jdk/Contents/Home/jre/ || fail
ln -s ${JDK}/Contents/Home/conf jdk-10.jdk/Contents/Home/jre/ || fail
ln -s ${JDK}/Contents/Home/jmods jdk-10.jdk/Contents/Home/jre/ || fail
ln -s ${JDK}/Contents/Home/lib jdk-10.jdk/Contents/Home/jre/ || fail
cp ${JDK}/Info.plist jdk-10.jdk/Contents/ || fail

rm -rf "OpenICP-BR.app" || fail
jar2app "$(basename "${JAR}")" -n "OpenICP-BR" -i ../other_res/osx/logo.icns -s "${VERSION}" -v "${VERSION}" -r jdk-10.jdk || fail
echo -e "${GREEN}Generated .app bundle ${NC}"

mkdir OpenICP-BR.app/Contents/Java/lib || fail
cp -arv lib/* OpenICP-BR.app/Contents/Java/lib || fail
echo -e "${GREEN}Copied java libraries to .app bunlde ${NC}" || fail

rm -rf dmg
mkdir dmg
ln -s /Applications ./dmg/Aplicativos || fail
cp -r OpenICP-BR.app ./dmg/ || fail
create-dmg OpenICP-BR.app --overwrite || fail
rm ./dmg/Aplicativos || fail
echo -e "${GREEN}Created DMG file${NC}"
