#!/bin/bash

# DEPENDENCY: https://github.com/sindresorhus/create-dmg
# DEPENDENCY: https://github.com/Jorl17/jar2app

# BUG: version is not being set
# BUG: adding a JRE does not work

GREEN='\033[0;32m'
NC='\033[0m' # No Color

VERSION=$1
JDK=/Library/Java/JavaVirtualMachines/jdk-10.jdk

# Get on the correct directory
cd target

# Prepare JRE for bundling
mkdir -p jdk-10.jdk/Contents/Home/jre/
ln -s ${JDK}/Contents/MacOS jdk-10.jdk/Contents
ln -s ${JDK}/Contents/Home/bin jdk-10.jdk/Contents/Home/jre/
ln -s ${JDK}/Contents/Home/conf jdk-10.jdk/Contents/Home/jre/
ln -s ${JDK}/Contents/Home/jmods jdk-10.jdk/Contents/Home/jre/
ln -s ${JDK}/Contents/Home/lib jdk-10.jdk/Contents/Home/jre/
cp ${JDK}/Info.plist jdk-10.jdk/Contents/

rm -rf "OpenICP-BR.app"
jar2app "ktApp-${VERSION}.jar" -n "OpenICP-BR" -i ../other_res/osx/logo.icns -s "${VERSION}" -v "${VERSION}" -r jdk-10.jdk || 
exit -1
echo -e "${GREEN}Generated .app bunlde ${NC}"

mkdir OpenICP-BR.app/Contents/Java/lib || exit -1
cp -arv lib/* OpenICP-BR.app/Contents/Java/lib || exit -1
echo -e "${GREEN}Copied java libraries to .app bunlde ${NC}" || exit -1

rm -rf dmg
mkdir dmg
ln -s /Applications ./dmg/Aplicativos || exit -1
cp -r OpenICP-BR.app ./dmg/ || exit -1
create-dmg OpenICP-BR.app --overwrite || exit -1
rm ./dmg/Aplicativos || exit -1
echo -e "${GREEN}Created DMG file${NC}"
