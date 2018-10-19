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
JDK=jdk-10.jdk
JDK_PATH=/Library/Java/JavaVirtualMachines/${JDK}/Contents
JAR2OPTS="--name 'OpenICP-BR' --icon ../other_res/osx/logo.icns --short-version '${VERSION}' --version '${VERSION}' --runtime ${JDK} --use-osx-menubar --low-res-mode --copyright='G Queiroz' "

fail () {
    echo -e "${RED}Got error code $? from previous command.${NC}"
    exit -1
}

if [ ! -f "${JAR}" ]; then
    echo -e "${RED}File ${BLUE}${JAR}${RED} does not exist.\nPlease run: mvn clean package${NC}"
    exit -1
fi

if [[ "$1" ]]; then
    JDK_PATH=$1
fi
echo -e "Using JDK on: ${JDK_PATH}"

# Get on the correct directory
cd target

# Prepare JRE for bundling
rm -vrf ${JDK}
mkdir -p ${JDK}/Contents/Home/jre/ || fail
ln -s ${JDK_PATH}/MacOS ${JDK}/Contents || fail
ln -s ${JDK_PATH}/Home/bin ${JDK}/Contents/Home/jre/ || fail
ln -s ${JDK_PATH}/Home/conf ${JDK}/Contents/Home/jre/ || fail
ln -s ${JDK_PATH}/Home/jmods ${JDK}/Contents/Home/jre/ || fail
ln -s ${JDK_PATH}/Home/lib ${JDK}/Contents/Home/jre/ || fail
cp -Hva ${JDK_PATH}/Info.plist ${JDK}/Contents/ || fail

rm -rf "OpenICP-BR.app" || fail
cp "$(basename "${JAR}")" "ktApp.jar"
jar2app "ktApp.jar" ${JAR2APP_OPTS} || fail
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
